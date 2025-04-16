import requests


def test_provide_something():
    assert requests.get("http://localhost:8000/results").json() == "toto"