package ru.sbtqa.tag.pagefactory.events;

import java.io.IOException;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.properties.Configuration;
import ru.sbtqa.tag.pagefactory.stepdefs.SetupStepDefs;

public class KillProcessesTask implements Task {

    private static final Logger LOG = LoggerFactory.getLogger(SetupStepDefs.class);
    private static final String DEFAULT_LOG_PROPERTIES_PATH = "src/test/resources/config/log4j.properties";
    private static final Configuration PROPERTIES = ConfigFactory.create(Configuration.class);

    @Override
    public void handle() {
        stopTasksToKill();
    }

    private void stopTasksToKill() {
        String tasks = PROPERTIES.getTasksToKill();
        if (!PageFactory.isSharingActive() && !tasks.isEmpty()) {
            for (String task : tasks.split(",")) {
                stopTask(task);
            }
        }
    }

    private void stopTask(String task) {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec("taskkill /IM " + task.trim() + " /F");
            } else {
                Runtime.getRuntime().exec("killall " + task.trim());
            }
        } catch (IOException e) {
            LOG.debug("Failed to kill " + task, e);
        }
    }
}
