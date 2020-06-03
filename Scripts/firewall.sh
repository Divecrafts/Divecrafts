#!/bin/bash
ufw allow 22
ufw allow 3306
ufw allow 80
ufw allow 3000
ufw allow 3003
ufw allow 25565
ufw allow 443
yes | sudo ufw enable
