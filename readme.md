# Spring Boot Example for the reading of a docker secret

Example on how one can read a password from a docker secret file.

### Run

Checkout the project and run ``mvn verify``

In the ``run`` directory you find run configs for your IDE. Import them, so you can start the project locally.
- `run app` - plain run config
- `run app with env` - run config with the ``SECRET_FILE_PATH=data/super-important-secret.txt`` set. So you can test the processor locally

Start the project with the `run app with env` config then you can use this link: http://localhost:8080/reveal-your-secrets,
and it will reveal the secret from the file to you. 

### Docker

Build the container with `mvn install` or `mvn docker:build`. This will create the image `spring-boot-docker-secrets:latest`.

Then you can run `docker-compose up` this will run the image with the secret added from the file in the `data` dir. 
With http://localhost:3000/reveal-your-secrets you can check that the secret was added and parsed.

#### External Docker Secret

With Docker one can set `docker secrets` unfortunately that feature is only available when a docker swarm is running. 
That isn't a big issue, every machine can initiate a swarm.

Simply run `docker swarm init`

Then create the secret with `echo "Helas, I'm from an external docker secret" | docker secret create important-secret -`

Deploy the service with `docker stack deploy -c docker-compose-external-secret.yml app`

Then again you can see the secret on http://localhost:4000/reveal-your-secrets

[Docker Secrets Doc](https://docs.docker.com/engine/swarm/secrets/)

##### Cleanup

```
# remove the deployed service
docker stack rm app
# delete the secret
docker secret rm important-secret
# leave/ disable the swarm
docker swarm leave -f
```


