package ${package.Controller};

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
<#if swagger2>
import io.swagger.annotations.Api;
</#if>
/**
* <p>
* ${table.comment!} 控制层
* </p>
*
* @author ${author}
* @since ${date}
*/
@Slf4j
@RestController
<#if swagger2>
@Api(tags = "${table.comment!}接口")
</#if>
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${table.entityPath}Service;

}
</#if>