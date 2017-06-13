package org.hulan.util.log.plugin;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.springframework.util.Assert;

/**
 * 功能描述：根据appendername过滤
 * 时间：2017/4/24 14:37
 *
 * @author ：zhaokuiqiang
 */
@Plugin(name = "dlNameFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public class DlNameFilter extends AbstractFilter{

    static String NAME = "name";
    String filterName;

    public DlNameFilter(String filterName,Result onMatch, Result onMismatch) {
        super(onMatch, onMismatch);
    }

    @Override
    public Result filter(LogEvent event) {
        return super.filter(event);
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    @PluginFactory
    public static DlNameFilter createFilter(
            @PluginElement("filterName") final String filterName,
            @PluginAttribute("onMatch") final Result match,
            @PluginAttribute("onMismatch") final Result mismatch){
        Assert.hasText(filterName,null);
        return new DlNameFilter(filterName,match,mismatch);
    }
}
