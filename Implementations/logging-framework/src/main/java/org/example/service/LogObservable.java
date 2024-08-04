package org.example.service;

import org.example.model.LogMessage;
import org.example.model.LogObserver;

public interface LogObservable {
    void addLogObserver(LogObserver logDestination);
    void removeLogObserver(LogObserver logDestination);
    void notifyLogObservers(LogMessage message);
}
