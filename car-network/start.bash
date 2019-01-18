 #!/bin/bash
composer archive create --sourceType dir --sourceName .
composer network install --archiveFile car-network@1.0.6-deploy.33.bna --card PeerAdmin@hlfv1
composer network start --networkName car-network --networkVersion 1.0.6-deploy.33 --networkAdmin admin --networkAdminEnrollSecret adminpw --card PeerAdmin@hlfv1 --file car-admin.card
