package com.tr.mita.process.service;

import com.tr.mita.base.exception.RespException;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcdefService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 部署流程
     * @param params
     * @return
     * @throws Exception
     */
    public String deploy(Map<String, String> params) throws Exception {
        String bpmnStr = params.get("bpmnStr");
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            Reader reader = new StringReader(bpmnStr);
            XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
            BpmnXMLConverter converter = new BpmnXMLConverter();
            BpmnModel bpmnModel = converter.convertToBpmnModel(streamReader);
            String key = bpmnModel.getMainProcess().getId();
            String name = bpmnModel.getMainProcess().getName();
            Deployment deployment = repositoryService.createDeployment()
                    .key(key)
                    .name(name)
                    .addString(key + ".bpmn", bpmnStr)
                    .deploy();

            return deployment.getId();
        } catch (Exception e) {
            throw new RespException();
        }
    }

    /**
     * 分页查询流程定义
     * @param params
     * @return
     */
    public Map<String, Object> queryListWithPage(Map<String, Object> params) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String key = "%" + (params.get("key") == null ? "" : (String)params.get("key")) + "%";
        String name = "%" + (params.get("name") == null ? "" : (String)params.get("name")) + "%";
        int page = (int)params.get("page");
        int limit = (int)params.get("limit");
        int begin = (page-1)*limit;
        List<ProcessDefinition> processDefinitions =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionNameLike(name)
                        .processDefinitionKeyLike(key)
                        .latestVersion()
                        .listPage(begin, limit);
        List<Map<String, Object>> bizdatas = new ArrayList<Map<String, Object>>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            Map<String, Object> bizdata = new HashMap<String, Object>();
            bizdata.put("id", processDefinition.getId());
            bizdata.put("key", processDefinition.getKey());
            bizdata.put("name", processDefinition.getName());
            bizdata.put("deploymentid", processDefinition.getDeploymentId());
            bizdata.put("version", processDefinition.getVersion());
            bizdatas.add(bizdata);
        }
        retMap.put("total", repositoryService.createProcessDefinitionQuery().latestVersion().list().size());
        retMap.put("bizdatas", bizdatas);
        return retMap;
    }

    /**
     * 通过key查询流程定义
     * @param key
     * @return
     */
    public List<Map<String, Object>> queryListByKey(String key) {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .orderByProcessDefinitionVersion()
                .desc().list();
        List<Map<String, Object>> bizdatas = new ArrayList<Map<String, Object>>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            Map<String, Object> bizdata = new HashMap<String, Object>();
            bizdata.put("id", processDefinition.getId());
            bizdata.put("key", processDefinition.getKey());
            bizdata.put("name", processDefinition.getName());
            bizdata.put("deploymentid", processDefinition.getDeploymentId());
            bizdata.put("version", processDefinition.getVersion());
            bizdatas.add(bizdata);
        }
        return bizdatas;
    }

    /**
     * 获取流程模型xml内容
     * @param procdefid
     * @return
     */
    public String getBpmn(String procdefid) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procdefid);
        BpmnXMLConverter converter = new BpmnXMLConverter();
        byte[] bytes = converter.convertToXML(bpmnModel);
        return new String(bytes);
    }

    /**
     * 删除流程定义
     * @param deploymentid   流程部署id
     */
    public void del(String deploymentid) {
        repositoryService.deleteDeployment(deploymentid);
    }

    /**
     * 通过key 删除流程定义
     * @param key
     */
    public void delByKey(String key) {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().processDefinitionKey(key).list();
        if (deployments!=null && deployments.size()>0) {
            for (Deployment deployment : deployments) {
                logger.info("deploymentid:" + deployment.getId());
            }
        }
    }

}
