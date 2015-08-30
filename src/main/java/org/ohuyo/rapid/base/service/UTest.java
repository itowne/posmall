package org.ohuyo.rapid.base.service;

import java.util.UUID;

public class UTest {
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(uuid.getLeastSignificantBits());
		System.out.println(uuid.getMostSignificantBits());
	}
}
