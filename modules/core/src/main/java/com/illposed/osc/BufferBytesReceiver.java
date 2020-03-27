/*
 * Copyright (C) 2017, C. Ramakrishnan / Illposed Software.
 * All rights reserved.
 *
 * This code is licensed under the BSD 3-Clause license.
 * See file LICENSE (or LICENSE.html) for more information.
 */

package com.illposed.osc;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * TODO
 */
public class BufferBytesReceiver implements BytesReceiver {

	private final ByteBuffer buffer;

	public BufferBytesReceiver(final ByteBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public BytesReceiver put(final byte b) {

		buffer.put(b);
		return this;
	}

	@Override
	public BytesReceiver put(final byte[] src) {

		buffer.put(src);
		return this;
	}

	@Override
	public BytesReceiver put(final ByteBuffer src) {

		buffer.put(src);
		return this;
	}

	@Override
	public BytesReceiver clear() {

		buffer.clear();
		return this;
	}

	@Override
	public int position() {
		return buffer.position();
	}

	// HACK try to get rid of this method
	public ByteBuffer getBuffer() {
		return buffer;
	}

//
//	@Override
//	public BytesReceiver position(final int newPosition) {
//
//		buffer.position(newPosition);
//		return this;
//	}

	private class PlaceHolderImpl implements PlaceHolder {

		private final int position;
		private final int size;

		PlaceHolderImpl(final int position, final int size) {

			this.position = position;
			this.size = size;
		}

		@Override
		public void replace(final byte[] src) throws OSCSerializeException {

			if (src.length != size) {
				throw new OSCSerializeException(String.format(
						"Trying to replace placeholder of size %d with data of size %d",
						size, src.length));
			}
			final int curPosition = buffer.position();
			buffer.position(position);
			put(src);
			buffer.position(curPosition);
		}
	}

	@Override
	public PlaceHolder putPlaceHolder(final byte[] src) {

		final PlaceHolderImpl placeHolder = new PlaceHolderImpl(position(), src.length);
		put(src);
		return placeHolder;
	}
//	public PlaceHolder registerPlaceHolder(int numBytes) {
//
//		return new PlaceHolderImpl(position(), numBytes);
//	}

	@Override
	public byte[] toByteArray() {

		buffer.flip(); // TODO check if this is always required
		final byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		return bytes;
	}
}
