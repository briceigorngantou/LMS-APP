<!-- Documentation pour deploiement du projet localement -->

1- Rassurez vous d'avoir installer docker sur votre machine

2- Ouvrez un powershell puis executer les commandes suivantes
    -> docker network create users_microservice
    -> docker network create enrollment_microservice
    -> docker network create course_microservice
    
3- Ouvrez les 3 microservices dans 3 powershells differents

4- Executer la commande ci-dessous dans chaque powerShell:
    -> docker compose up --build

5- Ouvrez un navigateur et accedez a la documentation swagger des 3 microservices via les urls suivantes:
    -> http://localhost:8080/swagger-ui/index.html
    -> http://localhost:8084/swagger-ui/index.html
    -> http://localhost:8086/swagger-ui/index.html