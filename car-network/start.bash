 #!/bin/bash
composer card delete --card admin@car-network
composer card import -f admin.card
composer archive create --sourceType dir --sourceName .
composer network install --archiveFile car-network.bna --card PeerAdmin@hlfv1
composer network start -c PeerAdmin@hlfv1 -V 1.0.6-deploy.32 -n car-network -A admin -S adminpw
