package apicore.integration;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import apicore.exception.Throw;


public class RestClient {

    private static Logger logger = LoggerFactory.getLogger(RestClient.class);

    private RestClient(){}
   
    public static <R> RestClientBuilder<R> get(String uri,Class<R> returnCalling){
        return new RestClientBuilder<>(HttpMethod.GET, uri,returnCalling);
    }

    public static <R> RestClientBuilder<R> post(String uri,Class<R> returnCalling){
        return new RestClientBuilder<>(HttpMethod.POST, uri,returnCalling);
    }

    public static <R> RestClientBuilder<R> put(String uri,Class<R> returnCalling){
        return new RestClientBuilder<>(HttpMethod.PUT, uri,returnCalling);
    }

    public static <R> RestClientBuilder<R> patch(String uri,Class<R> returnCalling){
        return new RestClientBuilder<>(HttpMethod.PATCH, uri,returnCalling);
    }

    public static <R> RestClientBuilder<R> delete(String uri,Class<R> returnCalling){
        return new RestClientBuilder<>(HttpMethod.DELETE, uri,returnCalling);
    }

    public static class RestClientBuilder<R> {

        public  RestClientBuilder<R> call(){
            try{
                callingHasBeenDone = true;
                logger.debug("call() : uri = {} , method = {} ,  returnCalling = {}", uri, method , returnCalling.getName());
                
                responseObject =  new RestTemplate().exchange(uri, method,  new HttpEntity<>(requestObject,headers), returnCalling, requestUriValue).getBody();
                logger.debug("call() : response sucess = {}", responseObject);
            
            }catch(HttpStatusCodeException exception){
                logger.debug("call() : response error code = {} , message = {}", exception.getStatusCode().value(), exception.getMessage());
                if(exception.getStatusCode().is5xxServerError()){
                    responseObject = onError5XX.apply(exception.getStatusCode(),defaultExtractMessageError.apply(exception));
                }else if(exception.getStatusCode().is3xxRedirection()){
                    responseObject = onError3XX.apply(exception.getStatusCode(),defaultExtractMessageError.apply(exception));
                }else if(exception.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                    responseObject = onError404.apply(defaultExtractMessageError.apply(exception));
                }else if(exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)){
                    responseObject = onError401.apply(defaultExtractMessageError.apply(exception));
                }else if(exception.getStatusCode().equals(HttpStatus.FORBIDDEN)){
                    responseObject = onError403.apply(defaultExtractMessageError.apply(exception));
                }else{
                    responseObject = onError5XX.apply(exception.getStatusCode(),defaultExtractMessageError.apply(exception));
                }
            }
            return this;
        }

        public R getResponse(){
            if( !callingHasBeenDone ) Throw.any(logger, HttpStatus.INTERNAL_SERVER_ERROR,"call() has not been done when getResponse()");
            return this.responseObject;
        }

        private RestClientBuilder(HttpMethod method, String uri,Class<R> returnCalling){
            this.uri = uri;
            this.method = method;
            this.returnCalling = returnCalling;

            headers = new LinkedMultiValueMap<>();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        }

        private final String uri;
        private final HttpMethod method;
        private final Class<R> returnCalling;


        private final MultiValueMap<String,String> headers;
        private Object[] requestUriValue = null;
        private Object requestObject = null;
        private R responseObject = null;
        private boolean callingHasBeenDone = false;


        private record ErrorDefaultResponse(String message){}
        private Function<HttpStatusCodeException,String> defaultExtractMessageError = e -> e.getResponseBodyAs(ErrorDefaultResponse.class).message();

        public  RestClientBuilder<R> changeDefaultExtractMessageError(Function<HttpStatusCodeException,String> extractMessageError){
            this.defaultExtractMessageError = extractMessageError;
            return this;
        }

        private BiFunction<HttpStatusCode,String,R> onError5XX = (status, message) -> {Throw.any(logger,status, message); return null;};
        private BiFunction<HttpStatusCode,String,R> onError3XX = (status, message) -> {Throw.any(logger,status, message); return null;};
        private Function<String,R> onError401 = message -> {Throw.any(logger, HttpStatus.UNAUTHORIZED, message); return null;};
        private Function<String,R> onError403 = message -> {Throw.any(logger, HttpStatus.FORBIDDEN, message); return null;};
        private Function<String,R> onError404 = message -> {Throw.any(logger, HttpStatus.NOT_FOUND, message); return null;};

        public  RestClientBuilder<R> onError5XX(BiFunction<HttpStatusCode,String,R> onError5XX){
            this.onError5XX = onError5XX;
            return this;
        }

        public  RestClientBuilder<R> onError3XX(BiFunction<HttpStatusCode,String,R> onError3XX){
            this.onError3XX = onError3XX;
            return this;
        }

        public  RestClientBuilder<R> onError401(Function<String,R> onError401){
            this.onError401 = onError401;
            return this;
        }

        public RestClientBuilder<R> onError403(Function<String,R> onError403){
            this.onError403 = onError403;
            return this;
        }

        public  RestClientBuilder<R> onError404(Function<String,R> onError404){
            this.onError404 = onError404;
            return this;
        }

        public RestClientBuilder<R> addHeader(String key, String value){
            this.headers.add(key, value);
            return this;
        }

        public RestClientBuilder<R> requestUriValue(String... value){
            this.requestUriValue = value;
            return this;
        }

        public RestClientBuilder<R> requestObject(Object requestObject){
            this.requestObject = requestObject;
            return this;
        }

    }

}
