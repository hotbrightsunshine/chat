# 💬 chat
This is the official repository of the *Chat* Project. 
Documentation can be found in the [wiki](https://github.com/hotbrightsunshine/chat/wiki).

# 👥 Collaborators
- [Davide Cazzato](https://github.com/Deivv77),
- [Francesco Parisio](https://github.com/hotbrightsunshine),
- [Lorenzo Salanitro](https://github.com/LorenzoSalanitro)

# 📚 Libraries used
- [Jackson](https://github.com/FasterXML/jackson), a JSON parser - POJO.

# 🎊 Latest Release
You can download our latest release [here](https://github.com/hotbrightsunshine/chat/releases/tag/v0.1.1).

# 🔮 How to run
## 🔒 Prerequisites 
To run our binaries, Java is needed. Consider downloading JDK version 17 LTS. If you are on Linux, to install Java dependencies correctly, use the package manager of your distro.
Headless versions should be fine. However, to be sure, install a complete JRE like we did. 

+ **Debian based**
    ```
    # apt install openjdk-17-jdk
    ```
+ **Arch Linux based** 
    ```
    # pacman -S jdk17-openjdk
    ```
+ **Mac OS X**
    
    Mac OS X installation hasn't been tested. We haven't found enough information about JRE, so here we recommend you to install a JDK (which is able to run Java as well, since it contains a JRE itself).
    ```
    $ brew install openjdk@17
    ```

+ **Windows**

    On Windows, installation might get trickier. We recommend you to install [this](https://www.azul.com/downloads/?version=java-17-lts&os=windows&package=jdk) OpenJDK distribution, which has served me well for a lot of time.

    On Windows, you might also need to set environment variables manually. DuckDuckGo it if you need to.

You can verify the correct installation of the JDK by typing 
```
$ java --version
```

You should be able to see an output similar to this one
```
openjdk 17.0.5 2022-10-18
OpenJDK Runtime Environment (build 17.0.5+1)
OpenJDK 64-Bit Server VM (build 17.0.5+1, mixed mode)
```

## 🔓 Running

Make sure to have followed the guide above in order to have a JVM installed in your machine. 

### Running client

Download the latest version of the client. `cd` into the folder the `client-vX.X.jar` is in, and then type: 
```
$ java -jar client-vX.X.jar <ip address> <port>
```
Capital X are to be substituted with the actual version you downloaded. Parameters in triangular braces (`<` and `>`) are mandatory. Braces needs to be removed. 

For instance, to make the client connect to `localhost` to a server listening on port `2022`, the following might be typed:
```
$ java -jar client.jar localhost 2022
```

### Running server

The same goes for the server. However, this doesn't need an IP to be launched. Port is the only parameter that has to be specified. 

For instance, to run a server listening on port `2022`:
```
$ java -jar server.jar 2022
``` 