🐌 Snail Race Kata - snail race bet (python)
====

The goal of this kata is to practice hexagonal architecture and adapter contract testing by
writing a bet application.

# Requirement setup

1. Start mongodb locally

```shell
docker run --rm -p 27017:27017 --name mongo_contract_testing -d mongo
```

2. Start the snail race server locally

Run the docker hub image
```shell
docker run --rm -d -p8000:8000 --name snail-race-server mathieucans/snail-race-server:latest
```

Verify the race server status
```shell
curl localhost:8000/results
```

3. Install python virtual environnement
```shell
python3 -m venv .venv
source .venv/bin/activate
```

4. Install dependencies
```shell
pip install -r requirements.txt
```

5. Check that requirements by run the following command
```shell    
pytest test/test_requirements_check.py
```
