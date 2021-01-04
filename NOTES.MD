# How to configure query dsl with JPA generation within gradle project:
* https://blog.jdriven.com/2018/10/using-querydsl-annotation-processor-with-gradle-and-intellij-idea/
`
    buildscript {
        repositories {
            maven {
                url "https://plugins.gradle.org/m2/"
            }
        }
        dependencies {
            classpath('net.ltgt.gradle:gradle-apt-plugin:0.18')
        }
    }
    
    apply plugin: 'net.ltgt.apt'
    apply plugin: 'net.ltgt.apt-idea'
    
    def queryDslVersion = '4.1.3'
    
    dependencies {
        compile("com.querydsl:querydsl-core:${queryDslVersion}")
        compile("com.querydsl:querydsl-jpa:${queryDslVersion}")
    }
    
    dependencies {
        compile "com.querydsl:querydsl-jpa:${queryDslVersion}"
        compileOnly 'org.projectlombok:lombok:1.16.18'
        annotationProcessor(
            "com.querydsl:querydsl-apt:${queryDslVersion}:jpa",
            "org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final",
            "javax.annotation:javax.annotation-api:1.3.2",
            "org.projectlombok:lombok"
        )
    }
`
