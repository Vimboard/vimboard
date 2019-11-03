package com.github.vimboard.config;

import javax.validation.ValidationException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class BoardListBean {

    private static final String INVALID_BOARDS = "Invalid '%s' property value";

    private final Map<String, Object[]> boardlistByBoard;

    public BoardListBean(VimboardProperties vimboardProperties) {
        boardlistByBoard = new HashMap<>();
        final Map allBoards = vimboardProperties.getAll().getBoards();
        boardlistByBoard.put("", parse("", allBoards));
        for (String boardUri : vimboardProperties.getCustom().keySet()) {
            final Map customBoards = vimboardProperties
                    .getCustom().get(boardUri).getBoards();
            boardlistByBoard.put(boardUri, (allBoards == customBoards)
                    ? boardlistByBoard.get("") : parse(boardUri, customBoards));
        }
    }

    public Object[] get(String boardUri) {
        return boardlistByBoard.get(boardUri);
    }

    private Object[] parse(String boardUri, Map boards) {
        if (boards == null) {
            return null;
        }
        final int len = boards.keySet().size();
        Object[] result = new Object[len];
        for (int i = 0; i < len; i++) {
            Object obj = boards.get(Integer.toString(i));
            if (obj == null) {
                throwInvalid(boardUri);
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.keySet().size() != 1) {
                    throwInvalid(boardUri);
                }
                for (Object key : map.keySet()) {
                    if (!(key instanceof String)) {
                        throwInvalid(boardUri);
                    }
                    Object value = map.get(key);
                    if (value instanceof Map) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, parse(boardUri, (Map) value));
                    } else if (value instanceof String) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, value);
                    } else {
                        throwInvalid(boardUri);
                    }
                }
            } else if (obj instanceof String) {
                result[i] = obj;
            } else {
                throwInvalid(boardUri);
            }
        }
        return result;
    }

    private void throwInvalid(String boardUri) {
        throw new ValidationException(String.format(INVALID_BOARDS,
                boardUri.isEmpty()
                        ? "vimboard.all.boards"
                        : "vimboard.custom." + boardUri + ".boards"));
    }
}
