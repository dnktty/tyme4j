package com.tyme.table;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Header {
    private List<String> headers;

    public void addHeader(String header) {
        if (headers == null) {
            headers = new ArrayList<>();
        }
        headers.add(header);
    }

    public void insertHeader(int index, String header) {
        if (headers == null) {
            headers = new ArrayList<>();
        }
        headers.add(index, header);
    }

    public void removeHeader(int index) {
        if (headers != null) {
            headers.remove(index);
        }
    }
}
