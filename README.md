## Incident resolution analyzer

... with Micronaut + AI

## Requirements

- Google Cloud Application
- Docker (in case you want to run in local Ollama)

## Idea

Say you are managing the Customer Complain department of your company

Say you have a bunch of internal emails (Gmail in this example) about a lot of complaining of customers, discussing
between different departments every one. 

Say you want to analyze all of them and decide if a complaining can be considered as closed or decide what's the next steps to run.

## "Solution"

We'll create a command line micronaut application and, after providing some keywords (for example, the name of the
customer), we'll retrieve all the relation conversations.
We will run a query to our AI agent to analyze the conversation, so it can give us some tips about next steps.

Finally, we'll store the answer into a database to future references

## Requirements

As we'll use Groogle library to access our Gmail account, we'll need a `client_secret.json` file with the credentials
(see Groogle for more information)

If you don't have (or don't want to use) OpenAI, you can use an Ollama docker image so the assistant will run completely 
into your systems. I've provided a docker-compose in the `ollama` folder

## Build

`./gradlew build`

## OpenAI

Configure `resources/application-openai.properties` with your personal token

# Ollama

in the `ollama` folder run `docker compose up -d`

Once the image is ready open a shell: 

`docker compose exec ollama bash`

And download the model:

`ollama pull gemma2:9b`  // Or the model you prefer

## Check

` ./gradlew run -Pcliente="Jorge Aguilera" -Penvs=ollama`
