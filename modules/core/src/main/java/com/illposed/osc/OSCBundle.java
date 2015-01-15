/*
 * Copyright (C) 2003-2014, C. Ramakrishnan / Illposed Software.
 * All rights reserved.
 *
 * This code is licensed under the BSD 3-Clause license.
 * See file LICENSE (or LICENSE.html) for more information.
 */

package com.illposed.osc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A bundle represents a collection of OSC packets
 * (either messages or other bundles)
 * and has a time-tag which can be used by a scheduler to execute
 * a bundle in the future, instead of immediately.
 * {@link OSCMessage}s are executed immediately.
 *
 * Bundles should be used if you want to send multiple messages to be executed
 * as soon as possible and in immediate succession to each other,
 * or you want to schedule one or more messages to be executed in the future.
 */
public class OSCBundle implements OSCPacket {

	private OSCTimeStamp timestamp;
	private List<OSCPacket> packets;

	/**
	 * Create a new empty OSCBundle with a timestamp of immediately.
	 * You can add packets to the bundle with addPacket()
	 */
	public OSCBundle() {
		this(OSCTimeStamp.IMMEDIATE);
	}

	/**
	 * Create an OSCBundle with the specified timestamp.
	 * @param timestamp the time to execute the bundle
	 */
	public OSCBundle(final OSCTimeStamp timestamp) {
		this(null, timestamp);
	}

	/**
	 * Creates an OSCBundle made up of the given packets
	 * with a timestamp of now.
	 * @param packets array of OSCPackets to initialize this object with
	 */
	public OSCBundle(final Collection<OSCPacket> packets) {
		this(packets, OSCTimeStamp.IMMEDIATE);
	}

	/**
	 * Create an OSCBundle, specifying the packets and timestamp.
	 * @param packets the packets that make up the bundle
	 * @param timestamp the time to execute the bundle
	 */
	public OSCBundle(final Collection<OSCPacket> packets, final OSCTimeStamp timestamp) {

		if (null == packets) {
			this.packets = new LinkedList<OSCPacket>();
		} else {
			this.packets = new ArrayList<OSCPacket>(packets);
		}
		this.timestamp = timestamp;
	}

	/**
	 * Return the time the bundle will execute.
	 * @return a Date
	 */
	public OSCTimeStamp getTimestamp() {
		return timestamp;
	}

	/**
	 * Set the time the bundle will execute.
	 * @param timestamp when the bundle should execute, <code>null</code> means IMMEDIATE
	 */
	public void setTimestamp(final OSCTimeStamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Add a packet to the list of packets in this bundle.
	 * @param packet OSCMessage or OSCBundle
	 */
	public void addPacket(final OSCPacket packet) {
		packets.add(packet);
	}

	/**
	 * Get the packets contained in this bundle.
	 * @return the packets contained in this bundle.
	 */
	public List<OSCPacket> getPackets() {
		return Collections.unmodifiableList(packets);
	}
}
