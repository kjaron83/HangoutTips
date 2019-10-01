# HangoutTips
This web application suggests interesting places to hang out with friends. It uses [IP2Location™ LITE database](https://lite.ip2location.com/ip2location-lite) to find the current visitor's location, and [Google Places API](https://cloud.google.com/maps-platform/places/) to provide the suggestions.

# Editing the project
To edit this project you need [Eclipse Java EE IDE for Web Developers](https://www.eclipse.org/).

# Environment requirements
* [Java 11 (OpenJDK 11.0.4)](http://openjdk.java.net/install/)
* [Apache Tomcat (9.0.22)](https://tomcat.apache.org/)
* [MySQL (5.7.27)](http://www.mysql.com/downloads/)
* [Google Places API Key](https://developers.google.com/places/web-service/get-api-key)

# Installing instructions
* Create the required environment.
* Create a database for the application.
* Download the [IP2Location™ LITE database](https://lite.ip2location.com/database/ip-country-region-city-latitude-longitude-zipcode-timezone) tables (ip2location_db11, ip2location_db11_ipv6) and import those to your database. 
* Import **src/main/resources/prepare.database.sql** file to your database as well.
* In the **src/main/resources** folder rename the **main.sample.properties** to **main.properties** and fill the file with correct setting values.
* Build the **.war** file and deploy it to Tomcat.

# License
[MIT](https://github.com/kjaron83/HangoutTips/blob/master/LICENSE)
