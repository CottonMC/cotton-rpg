package io.github.cottonmc.cottonrpg.data;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public abstract class ProxyRpgDataContainer<T extends RpgDataType, E extends RpgDataEntry<T>> extends BaseRpgDataContainer<T, E> {
	private final RpgDataContainer<T, E> parent;
	private final RpgDataContainer<T, E> child;

	public ProxyRpgDataContainer(RpgDataContainer<T, E> parent, @Nullable RpgDataContainer<T, E> child) {
		this.parent = parent;
		this.child = child;
	}

	@Override
	public int size() {
		return parent.size() + (child != null? child.size() : 0);
	}

	@Override
	public boolean has(T skill) {
		return super.has(skill);
	}

	@Override
	public boolean has(Identifier id) {
		if (child != null && child.has(id)) return true;
		else return parent.has(id);
	}

	@Override
	public E get(T skill) {
		return super.get(skill);
	}

	@Override
	public E get(Identifier id) {
		if (child != null && child.has(id)) return child.get(id);
		else return parent.get(id);
	}

	@Override
	public void forEach(BiConsumer<Identifier, E> consumer) {
		if (child != null) child.forEach(consumer);
		parent.forEach(consumer);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Can't clear data from the proxy! Get the parent or child instead!");
	}

	@Override
	public E remove(T skill) {
		throw new UnsupportedOperationException("Can't remove data from the proxy! Get the parent or child instead!");
	}

	@Override
	public E remove(Identifier id) {
		throw new UnsupportedOperationException("Can't remove data from the proxy! Get the parent or child instead!");
	}

	@Override
	public E giveIfAbsent(Identifier id) {
		throw new UnsupportedOperationException("Can't add data from the proxy! Get the parent or child instead!");
	}

	@Override
	public boolean isDirty() {
		throw new UnsupportedOperationException("The proxy can never be dirty! Get the parent or child instead!");
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf) {
		throw new UnsupportedOperationException("Can't sync a proxy! Get the parent or child instead!");
	}

	@Override
	public void applySyncPacket(PacketByteBuf buf) {
		throw new UnsupportedOperationException("Can't sync a proxy! Get the parent or child instead!");
	}

	public RpgDataContainer<T, E> getParent() {
		return parent;
	}

	public RpgDataContainer<T, E> getChild() {
		return child;
	}
}
