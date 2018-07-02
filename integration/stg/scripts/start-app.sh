#!/usr/bin/env bash

sudo chmod +x /home/ec2-user/okky-notification-1.0.0.jar
sudo ln -sf /home/ec2-user/okky-notification-1.0.0.jar /etc/init.d/okky-notification
sudo service okky-notification start
sleep 10s