package com.tanli.cloud.controller.kubernetes;

import com.tanli.cloud.service.NodeService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableAutoConfiguration
@Controller
@RequestMapping("kubernetes")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @RequestMapping(value = {"/", ""})
    public ModelAndView index(){
        return new ModelAndView("kubernetes/node");
    }

    @RequestMapping("nodesinfo")
    @ResponseBody
    public APIResponse getNodes(HttpServletRequest request, HttpServletResponse response){
        return nodeService.getNodes();
    }
}
