package com.example.couple.Model.Support;

import com.example.couple.Model.Bridge.Couple.TriadBridge;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TriadSets {
    int size;
    List<TriadBridge> triadBridgeList;
}
