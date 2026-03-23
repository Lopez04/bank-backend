package com.example.utils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtils {
private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

private static final int ITERATIONS = 3;
private static final int MEMORY = 65536; // 64MB
private static final int PARALLELISM = 1;

public PasswordUtils (){}

public static String hash(String pass){
    char[] p = pass.toCharArray();

    try{
        return argon2.hash(ITERATIONS,MEMORY,PARALLELISM,p);
    }finally {
        argon2.wipeArray(p);
    }
}

public static Boolean verify(String pass, String storeHash){
    return argon2.verify(storeHash, pass.toCharArray());
}
}
