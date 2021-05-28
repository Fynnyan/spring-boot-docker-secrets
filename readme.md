# Spring Boot Example for the reading of a docker secret

Example on how one can read a password from a docker secret file.

# run

Checkout the project and run ``mvn verify``

In the ``run`` directory you find run configs for your IDE. Import them, so you can start the project locally.
- `run app` - plain run config
- `run app with env` - run config with the ``SECRET_FILE_PATH=data/super-important-secret.txt`` set. So you can test the processor locally

Start the project with the `run app with env` config then you can use this link: http://localhost:8080/reveal-your-secrets,
and it will reveal the secret from the file to you. 
