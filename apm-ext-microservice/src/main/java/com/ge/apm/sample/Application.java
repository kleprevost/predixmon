/******************************************************************************
 * Copyright (c) 2015 GE Digital. All rights reserved.                *
 *                                                                            *
 * The computer software herein is the property of GE Global Research. The    *
 * software may be used and/or copied only with the written permission of     *
 * GE Global Research or in accordance with the terms and conditions          *
 * stipulated in the agreement/contract under which the software has been     *
 * supplied.                                                                  *
 ******************************************************************************/

package com.ge.apm.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.ge.apm.sample"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
