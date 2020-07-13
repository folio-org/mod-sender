## 2020-07-13 v1.3.1
 * Fix notices for patrons with custom fields (MODSENDER-30)
 * Allow additional fields in user schema (MODSENDER-33)

## 2020-06-11 v1.3.0
 * MODSENDER-28 Update RMB version to 30.0.2 and Vertx to 3.9.1

## 2019-04-12 v1.2.0
 * MODSENDER-24 Update RMB version
 * MODSENDER-23 Use JVM features to manage container memory
 * FOLIO-2321 Remove old MD metadata 
 * MODSENDER-21 mod-sender does not purge tenant data
 * MODSENDER-20 mod-sender leaks WebClient objects
 * FOLIO-2256 enable kube-deploy pipeline for platform-core modules
 * FOLIO-2234 Add LaunchDescriptor settings
 * MODSENDER-16 Performance testing - initial setup for performance tests

## 2019-06-11 v1.1.0
 * Add links to README additional info (FOLIO-473)
 * Initial module metadata (FOLIO-2003)
 
## 2018-11-07 v1.0.0
 * Endpoint **POST /message-delivery** was implemented
 * DeliveryVerticle class was implemented to register delivery channels
 * Email delivery channel was registered with name 'email'
 
 | METHOD |  URL                          | DESCRIPTION                                                       |
 |--------|-------------------------------|-------------------------------------------------------------------|
 | POST   | /message-delivery             | Message delivery endpoint                                         |
