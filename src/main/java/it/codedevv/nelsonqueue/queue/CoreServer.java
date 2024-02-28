package it.codedevv.nelsonqueue.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CoreServer {
    private final String serverName;
    private final List<CorePlayer> queuePlayers = new ArrayList<>();
}