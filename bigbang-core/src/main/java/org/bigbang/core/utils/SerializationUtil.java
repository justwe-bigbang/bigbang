package org.bigbang.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;


/**
 * @Title SerializationUtil.java
 * @Description 基于 Protostuff 实现的序列化
 * @Author yzh yingzh@getui.com
 * @Date 05.09.2016
 */
public class SerializationUtil{

	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

	private static Objenesis objenesis = new ObjenesisStd(true);
	
	private static  <T> Schema<T> getSchema(Class<T> cls) {
		Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(cls);
			if (schema != null) {
				cachedSchema.put(cls, schema);
			}
		}
		return schema;
	}

    /**
     * 序列化（对象 -> 字节数组）
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the byte [ ]
     */
    public static  <T> byte[] serialize(T obj) {
		Class<T> cls = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer
				.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Schema<T> schema = getSchema(cls);
			return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

    /**
     * 反序列化（字节数组 -> 对象）
     *
     * @param <T>  the type parameter
     * @param data the data
     * @param cls  the cls
     * @return the t
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
		try {
			T message = (T) objenesis.newInstance(cls);
			Schema<T> schema = getSchema(cls);
			ProtostuffIOUtil.mergeFrom(data, message, schema);
			return message;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}