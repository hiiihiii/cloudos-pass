package com.tanli.cloud.model.Kubernetes;

import com.tanli.cloud.model.DeployContainer;

import java.util.Map;

public class Container {
    private String[] args;
    private String[] command;
    private EnvVar[] env;
    private String image;
    private String imagePullPolicy;
    private String name;
    private ContainerPort[] ports;
    private ResourcesRequirements resources;
    private String workingDir;

    public Container() { }

    public Container( String[] args,
                      String[] cmd,
                      EnvVar[] env,
                      String image,
                      String imagePullPolicy,
                      String name,
                      ContainerPort[] ports,
                      ResourcesRequirements resources,
                      String workingDir ) {
        this.args = args;
        this.command = cmd;
        this.env = env;
        this.image = image;
        this.imagePullPolicy = imagePullPolicy;
        this.name = name;
        this.ports = ports;
        this.resources = resources;
        this.workingDir = workingDir;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }

    public EnvVar[] getEnv() {
        return env;
    }

    public void setEnv(EnvVar[] env) {
        this.env = env;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePullPolicy() {
        return imagePullPolicy;
    }

    public void setImagePullPolicy(String imagePullPolicy) {
        this.imagePullPolicy = imagePullPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContainerPort[] getPorts() {
        return ports;
    }

    public void setPorts(ContainerPort[] ports) {
        this.ports = ports;
    }

    public ResourcesRequirements getResources() {
        return resources;
    }

    public void setResources(ResourcesRequirements resources) {
        this.resources = resources;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }
}
