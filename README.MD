Création d'une application SpringBoot
=====================================
Pour créer une application déployable à l'aide de SpringBoot, il suffit de créer une classe contenant une méthode main et de l'annoter avec '@SpringBootApplication'. Il faut évidemment avoir tirer les dépendances nécessaires à l'utilisation de SpringBoot.  
Ensuite, il est possible d'utiliser des frameworks tels que SpringMVC pour que le conteneur puisse rendre des pages HTML templatées grâce à Thymeleaf.  

On peut aussi créer des Controller qui permettront de faire le mapping avec les templates HTML en ajoutant les données nécessaires au model (HomeController).  

On peut enfin ajouter des RestController qui permette d'exposer des services REST au travers du conteneur porté par SpringBoot (ChapterController).

Facilité de développement
=========================
LiveReload
----------
Utilisation de LiveReload uniquement sur Chrome.
Le plugin sur Firefox ne fonctionne pas correctement.

Spring dev tools
----------------
L'utilisation de SpringDevTools accroît fortement la vitesse de développement.
Le redéploiement automatique de l'application associé à LiveReload permet d'avoir un rendu immédiat.

SpringRemoteApplication
-----------------------
En cas de déploiement de l'application sur un environnement non local, il suffit d'ajouter 'spring.devtools.remote.secret=learning-spring-boot' au fichier application.properties.  
Ensuite, il faut démarrer le projet à l'aide de la main class 'SpringRemoteApplication' (avec le projet dans le classpath).  
Ceci permet de redéployer à chaud les modifications locales sur le serveur distant.
Feature très intéressante dans le cas d'une version déployée en recette ou homologation.

Administration sur SpringBoot
=============================
Endpoints d'administration
--------------------------
En ajoutant les dépendances 'org.springframework.boot:spring-boot-actuator' et 'org.springframework.boot:spring-boot-starter-remote-shell' dans le build, on ajoute des endpoints sur le conteneur qui ajoutent des fonctions simples d'administration.
Par exemple :
*   '/health' permet d'avoir un état de santé de l'application.
    *   Il est possible de customiser cet endpoint en implémentant la classe HealthIndicator
*   '/autoconfig' permet d'avoir un rapport de ce que contient le classpaht du conteneur. Il est facile de trouver le conteneur utilisé (tomcat, undertow...).
    *   Il est aussi possible d'afficher le rapport de configuration au démarrage du conteneur en ajoutant '--debug' au variables de démarrage du programme.
*   '/beans' affiche l'ensemble des beans initialisés
*   '/env' affiche l'ensemble des informations d'environnement (JVM, OS, Port ...).
*   '/configprops' affiche l'ensemble des propriétés de configuration.
*   '/metrics' affiche des métriques simples sur le conteneur.
    *   Il est possible d'ajouter des metrics customisées telles qu'un compteur d'appel d'un service par exemple.Ceci permet d'avoir une vue rapide d'indicateurs importants pour l'application. Pour cela, il suffit d'injecter des objets tels que le 'CounterService' (pour incrémenter des compteurs), le 'GaugeService' (pour modifier la valeur liée à une clé) ou le 'InMemoryMetricRepository' (pour incrémenter un delta, le nombre de bytes téléchargés par exemple).
*   '/dump' crée un thread dump.
*   '/info' affiche des informations présentées par l'applications s'il y en a.
    *   A l'aide du plugin 'com.gorylenko.gradle-git-properties', on peut enrichir les informations et ainsi identifier aisément la version déployée. Les informations qui sont ajoutées sont des informations liées à git.
*   '/mappings' affiche les endpoints offerts par le conteneur.
*   '/trace' affiche l'ensemble des requêtes reçues par le conteneur.
*   '/actuator' permet d'aggréger tous les liens d'administration dans une seule page web. Cet endpoint nécessite la dépendance 'org.springframework.hateoas:spring-hateoas'.
    *   Il est possible de customiser cette page et d'y ajouter des indicateurs : http://fazlansabar.blogspot.fr/2015/02/hateoas-rest-apis-with-springboot.html

Changement du conteneur
-----------------------
Par défaut, Spring Boot embarque un conteneur tomcat. Il est possible de modifier ce conteneur à l'aide des dépendances (maven ou gradle).  
Il faut exclure le module 'spring-boot-starter-tomcat' des configurations et ajouter la dépendance 'org.springframework.boot:spring-boot-starter-undertow' pour utiliser un conteneur undertow par exemple.

CRaSH
-----
CRaSH propose des commandes SSH pour manager une instance de Spring Boot via des commandes SSH.
Le mot de passe à utiliser est présenté par défaut dans la log de démarrage du Spring Boot.
Le port par défaut est le port 2000.
Il est certainement possible de customiser ça.
Par défaut, CRaSH propose quelques commandes :
*   help : propose l'ensemble des commandes disponibles.
*   dashboard : propose un graphique, présent les threads, les propriétés de l'environnement, la mémoire ...
*   autoconfig : présente les règles d'autoconfiguration du Spring Boot qui sont appliquées et celles qui ne le sont pas.

Il est possible de créer ses propres commandes. Pour ce faire, il suffit de les déposer dans le répertoire 'resources/commands'.
Ceci permet par exemple d'accéder aux '/metrics' ou au '/health' proposés par actuator mais en SSH.

Une fois l'application redéployée, il suffit de s'y connecter en SSH : 'ssh - p 2000 user@localhost'.
Le mot de passe est celui indiqué dans les logs de démarrage du Spring Boot. A l'aide de la commande 'help', on peut accéder aux commandes disponibles. Même celles créées par nos soins.

Spring Cloud
============
Eureka Server
-------------
Pour construire un serveur Eureka (qui joue le rôle d'aggrégateur de services autodéclarés), il suffit d'ajouter l'annotation "@EnableEurekaServer" à la Spring Boot Application après avoir tirer la dépendance "org.springframework.cloud:spring-cloud-starter-eureka-server".

Il est possible de customisé le serveur eureka à l'aide de propriétés dans le fichier de configuration de l'application (par exemple le délai de rafraîchissement du serveur).

Eureka Client
-------------
Pour déclarer un client Eureka, il suffit d'annoter la Spring Boot Application à l'aide de l'annotation '@EnableEurekaClient'.

Il est ensuite possible de configurer le client eureka via le fichier de configuration.  
On peut par exemple modifier l'url du serveur eureka (s'il n'est pas déployé par défaut : http://localhost:8761/eureka). 

Ensuite, dès que l'application démarre, elle s'autodéclare sur le serveur eureka.

Il est ensuite possible d'injecter des éléments tels que le discoveryClient. Celui ci va exposer des méthodes permettant d'accéder aux informations des services déclarés sur le serveur eureka.  
On peut par exemple récupérer un service à l'aide de son nom : 'discoveryClient.getInstances("Service")'. Cela permet de récupérer l'ensemble des instances 'ups' de ce service déclarées sur le serveur eureka.  
Cela permet donc de créer des services hautement disponibles. Pour le client, il n'est pas important de connaiître l'ensemble des instances d'un service, juste d'en demander une 'up' au serveur eureka. Une fois les informations de l'instance récupérées, il est possible de l'appeler.

Spring Boot Admin
=================
Attention à la gestion des dépendances
--------------------------------------
L'utilisation de Spring Boot Admin (en version 1.4.5 actuellement) impose de se limiter à la version 1.4.3 de Spring Boot et la version 1.2.4 de Spring Cloud.
Pour Spring Cloud, j'ai utilisé la Release Camden qui englobe des versions compatibles.

L'utilisation de Spring Boot Admin permet d'ajouter des fonctionnalités de management au travers d'une interface Web. On a accès directement à l'ensemble des metrics et info sur les applications. 

La découverte des applications peut se faire au travers du serveur Eureka. Cela permet de voir l'ensemble des applications avec un minimum de configuration (l'url d'accès au serveur eureka).  
Pour permettre d'accéder à l'ensemble des applications déclarées sur un serveur eureka, il suffit d'ajouter l'annotation '@EnableDiscoveryClient' à la Spring Boot Application portant la Spring Boot Admin.  
Il est possible de déployer la Spring Boot Admin dans le même conteneur que le serveur eureka en modifiant le context path 'spring.boot.admin.context-path' dans le fichier de configuration.

Les informations affichées dans la console d'adminsistration sont diverses.

Pour le nom, il faut le renseigner dans le fichier de configuration de l'application 'spring.application.name'.  
Pour la version, il faut générer le fichier 'build-info.properties' à l'aide de la commande :
```gradle
springBoot {
    buildInfo()
}
```

Pour accéder aux éléments JMX, il faut que l'application cliente tire la dépendance 'org.jolokia:jolokia-core'. Cela permet d'accéder aux composants JMX au travers de la console d'administration.

Pour accéder aux loggers, il faut utiliser logback et ajouter le jmxConfigurator dans la configuration logback. On peut ensuite modifier les niveaux des différents loggers directement depuis la console d'administration.

Spring Cloud Hystrix
====================
Le circuit breaker proposé avec Spring Cloud permet d'éviter de continuer à faire des appels à un service lorsque l'on sait que la ressource n'est pas disponible.  

Pour ce faire, il faut externaliser l'appel du service dans une classe annotée '@Service'. On annote ensuite la méthode qui va faire appel au service distant à l'aide du 'RestTemplate' à l'aide de l'annotation '@HystrixCommand'.  

Cette annotation doit référencer une méthode de failover qui a la même signature que la méthode appelée. C'est cette méthode qui sera appelée tant que le circuit entre le client et le service est coupé sans avoir à refaire un appel à la ressource down. Le passage par la méthode de failover est transparent et aucune action n'est nécessaire lorsque la ressource est de nouveau disponible.

Questions pour Régis
====================