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
├── core
│   ├── api-contracts/src/apicontracts
│   │           │ 
│   │           ├── account
│   │           │   ├── CreateAccountContract.java
│   │           │   ├── GetAccountByIdContract.java
│   │           │   └── GetAccountByNameContract.java
│   │           ├── contentaddevent
│   │           │   └── AddEventContract.java
│   │           ├── contentonhotevent
│   │           │   └── GetAllEventAfterEventContract.java
│   │           ├── document
│   │           │   └── ListDocumentContract.java
│   │           ├── documentdraft
│   │           │   └── CreateDraftDocumentContract.java
│   │           ├── documentpublish
│   │           │   └── PublishDocumentContract.java
│   │           ├── session
│   │           │   ├── GetInfoSessionContract.java
│   │           │   ├── LoginSessionContract.java
│   │           │   └── LogoutSessionContract.java
│   │           └── storage
│   │               ├── GetContentFromStorageContract.java
│   │               └── SaveContentIntoStorageContract.java
│   └── api-core/src/apicore
│               │
│               ├── access
│               │   ├── AccessIntercept.java
│               │   ├── AccessType.java
│               │   ├── JwtUtils.java
│               │   └── SessionRequest.java
│               ├── config
│               │   └── ConfigApi.java
│               ├── environment
│               │   ├── PropertiesAppRegister.java
│               │   └── PropertiesDefault.java
│               ├── exception
│               │   ├── ApiException.java
│               │   ├── ExceptionRestHandler.java
│               │   └── Throw.java
│               ├── integration
│               │   └── RestClient.java
│               └── repository
│                   └── MongoRepository.java
└── microservices
    ├── account-ms/src/account
    │           ├── App.java
    │           ├── model
    │           │   └── Account.java
    │           └── service
    │               ├── CreateAccountService.java
    │               ├── GetAccountByIdService.java
    │               └── GetAccountByNameService.java
	│ 
    ├── content-add-event-ms/src/contentaddevent
    │           ├── App.java
    │           ├── model
    │           └── service
    │               └── AddEventService.java
	│
    ├── content-on-hot-event-ms/src/contentonhotevent
    │           ├── App.java
    │           ├── model
    │           └── service
    │               └── GetAllEventAfterEventService.java
	│
    ├── document-draft-ms/src/documentdraft
    │           ├── App.java
    │           └── service
    │               └── CreateDraftDocumentService.java
	│
    ├── document-ms/src/document
    │           ├── App.java
    │           └── service
    │               └── ListDocumentService.java
	│
    ├── document-publish-ms/src/documentpublish
    │           ├── App.java
    │           └── service
    │               └── PublishDocumentService.java
	│
    ├── session-ms/src/session
    │           ├── App.java
    │           ├── model
    │           │   └── Session.java
    │           └── service
    │               ├── GetInfoSessionService.java
    │               ├── LoginSessionService.java
    │               └── LogoutSessionByIdService.java
	│
    └── storage-ms/src/storage
                ├── App.java
                └── service
                    ├── GetContentFromStorageService.java
                    └── SaveContentIntoStorageService.java

# tree -I bin -I build -I build.gradle -I src-resource -I settings.gradle -I gradle -I gradlew -I gradlew.bat -I README.md
				
```