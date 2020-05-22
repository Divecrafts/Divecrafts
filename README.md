# Spigot
## Guía de uso

**·No quitar derechos de autor**

**·Leer bien la licencia de uso**


## Cómo usar
**- Creamos proyecto en maven**

**- Vamos al archivo:** `pom.xml`

En el escribimos lo siguiente:

 ```xml
    <!-- SPIGOT-REPO -->
    <repositories>
        <repository>
            <id>spigot-repo</id>
            <url>https://divecrafts.github.io/spigot/</url>
        </repository>
        <repository>
            <id>main-repo</id>
            <url>https://clonalejandro.github.io/Divecrafts/Libs/</url>
        </repository>
    </repositories>


    <!-- APIS -->
    <dependencies>
        <dependency>
            <groupId>net.divecrafts</groupId>
            <artifactId>spigot</artifactId>
            <version>1.8.8</version>
        </dependency>
    </dependencies>
 ```

<br>

  
**- Dejamos que se actualice:** `pom.xml`

Ya tendremos nuestra librería importada:
 
 ```php
     │
     ├── GitHub
     │   ├── Spigot.jar
     │   ├── pom.xml
     │   └── estructura
     │   
     ├── Spigot
     │   ├── BungeeCord-API
     │   ├── Spigot-API
     │   ├── Bukkit-API
     │   └── CraftBukkit-API     
     │
     ├── APIS
     │   ├── .jar oficial de divecrafts
     │   ├── .jar aprueba de exploits 
     │   └── .jar con todas las libs
     │
     └── Optimizado
 ```
 
 
 
![picture](https://i.imgur.com/1mIWzya.png)


## Copyright ©
#### Desarrollado por clonalejandro
