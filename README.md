# microservices-document-editor

## Setup project locally

Run gradlew build

```bash
# build all modules
./gradlew clean build

# build specific module
./gradlew :{any-module}:clean ./gradlew :{any-module}:build

```

> If you are going to use vscode IDE just choose `All services` composed laucher from `./.vscode/launch.json` to be run then all microservices will start up locally using all environment variables from `./env/env.dev`

## Start all services locally with dockercompose

```bash
docker-compose --env-file ./env/env.dev --file ./container/docker-compose.yml up -d
```

## See endpoints in swagger

All microservices have their swagger doc at `http://localhost:{PORT}/swagger`

## Tree archtecture

```bash
.
├── container
│   ├── core-parent-image.Dockerfile
│   ├── docker-compose.yml
│   └── microservices.Dockerfile
│
├── core
│   ├── api-contracts/src/apicontracts
│   │           └── dto
│   │               ├── AccountMS.java
│   │               └── SessionMS.java
│   │
│   └── api-core/src/apicore
│               ├── access
│               │   ├── AccessIntercept.java
│               │   ├── AccessType.java
│               │   ├── JwtUtils.java
│               │   └── SessionRequest.java
│               ├── config
│               │   └── ConfigApi.java
│               ├── environment
│               │   └── PropertiesDefault.java
│               ├── exception
│               │   ├── ApiException.java
│               │   ├── ExceptionRestHandler.java
│               │   └── Throw.java
│               ├── integration
│               │   └── RestClient.java
│               └── repository
│                   └── MongoRepository.java
│   
└── microservices
    ├── account-ms/src/account
    │           ├── App.java
    │           ├── controller
    │           │   └── AccountController.java
    │           ├── model
    │           │   └── Account.java
    │           └── service
    │               ├── CreateAccountService.java
    │               ├── GetAccountByIdService.java
    │               └── GetAccountByNameService.java
	│
    └── session-ms/src/session
                ├── App.java
                ├── controller
                │   └── SessionController.java
                ├── model
                │   └── Session.java
                └── service
                    ├── GetInfoSessionService.java
                    ├── LoginSessionService.java
                    └── LogoutSessionByIdService.java

# tree -I bin -I build -I build.gradle -I src-resource -I settings.gradle -I gradle -I gradlew -I gradlew.bat -I README.md
				
```