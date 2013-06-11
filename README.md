Xebia Bigdata Mongo
==============

Import tweets into mongo:

curl -o tweets-bigdata-sample.json https://dl.dropboxusercontent.com/u/7521332/tweets-bigdata-sample.json
mongoimport -d xebia -c tweet --drop < tweets-bigdata-sample.json