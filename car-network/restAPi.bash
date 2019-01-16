 #!/bin/bash
export COMPOSER_PROVIDERS='{
  "github": {
    "provider": "github",
    "module": "passport-github",
    "clientID": "c5998b6632a83ffc99aa",
    "clientSecret": "42d6d0bf3fb4e56195acb457c6e646c746cf5ab3",
    "authPath": "/auth/github",
    "callbackURL": "/auth/github/callback",
    "successRedirect": "/",
    "failureRedirect": "/"
  }
}'

export COMPOSER_DATASOURCES='{
  "db": {
    "name": "db",
    "connector": "memory",
    "file": "mydata.json"
  }
}'

composer-rest-server -c  admin@car-network -n never -a true -m true -u true
