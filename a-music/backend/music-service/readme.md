## How to run the app in docker:
1. Create .env file in src folder and copy the corresponding settings from .env.example file into .env file
2. Comment user-service, user-db and nginx containers
3. Build the containers with ```docker-compose build``` command
4. Run the app with run.sh script

P.s. Before commiting changes don't forget to uncomment containers