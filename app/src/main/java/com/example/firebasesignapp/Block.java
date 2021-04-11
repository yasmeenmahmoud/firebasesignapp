package com.example.firebasesignapp;

public class Block {
    public String block_name;
    public String block_address;
    public String user_id;

    public Block() {
    }

    public Block(String block_name, String block_address, String user_id) {
        this.block_name = block_name;
        this.block_address = block_address;
        this.user_id = user_id;
    }
}
