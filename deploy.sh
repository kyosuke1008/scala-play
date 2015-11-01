#!/bin/sh
sudo scp -i /Users/ishizukyousuke/Documents/summary.pem -r conf ec2-user@54.178.172.163:/tmp
sudo scp -i /Users/ishizukyousuke/Documents/summary.pem -r app ec2-user@54.178.172.163:/tmp
sudo scp -i /Users/ishizukyousuke/Documents/summary.pem -r public ec2-user@54.178.172.163:/tmp
sudo scp -i /Users/ishizukyousuke/Documents/summary.pem -r activator-launch-1.3.6.jar ec2-user@54.178.172.163:/tmp
sudo scp -i /Users/ishizukyousuke/Documents/summary.pem -r build.sbt ec2-user@54.178.172.163:/tmp
echo "デプロイ完了"
