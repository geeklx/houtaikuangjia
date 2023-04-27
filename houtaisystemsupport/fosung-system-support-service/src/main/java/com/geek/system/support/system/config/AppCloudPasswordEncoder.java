package com.geek.system.support.system.config;

import com.geek.system.support.util.service.AppCloudPasswordDigester;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.nimbusds.jose.util.ByteUtils.subArray;
import static org.springframework.security.crypto.util.EncodingUtils.concatenate;

/**
 *
 * 密码解析
 *
 * @author liuke
 * @date  2022/2/7 15:53
 * @version
*/
public final class AppCloudPasswordEncoder implements PasswordEncoder {

	private final AppCloudPasswordDigester digester;

	private final byte[] secret;

	private final BytesKeyGenerator saltGenerator;

	public AppCloudPasswordEncoder() {
		this("");
	}

	public AppCloudPasswordEncoder(CharSequence secret) {
		this("SHA-256", secret);
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return encode(rawPassword, saltGenerator.generateKey());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//return true ;
		byte[] digested = decode(encodedPassword);
		byte[] salt = subArray(digested, 0, saltGenerator.getKeyLength());
		return matches(digested, digest(rawPassword, salt));
	}

	private AppCloudPasswordEncoder(String algorithm, CharSequence secret) {
		this.digester = new AppCloudPasswordDigester(algorithm, DEFAULT_ITERATIONS);
		this.secret = Utf8.encode(secret);
		this.saltGenerator = KeyGenerators.secureRandom();
	}

	private String encode(CharSequence rawPassword, byte[] salt) {
		byte[] digest = digest(rawPassword, salt);
		return new String(Hex.encode(digest));
	}

	private byte[] digest(CharSequence rawPassword, byte[] salt) {
		byte[] digest = digester.digest(concatenate(salt, secret,
				Utf8.encode(rawPassword)));
		return concatenate(salt, digest);
	}

	private byte[] decode(CharSequence encodedPassword) {
		return Hex.decode(encodedPassword);
	}

	/**
	 * Constant time comparison to prevent against timing attacks.
	 */
	private boolean matches(byte[] expected, byte[] actual) {
		if (expected.length != actual.length) {
			return false;
		}

		int result = 0;
		for (int i = 0; i < expected.length; i++) {
			result |= expected[i] ^ actual[i];
		}
		return result == 0;
	}

	private static final int DEFAULT_ITERATIONS = 1024;

	public static void main(String[] args) {
		AppCloudPasswordEncoder appCloudPasswordEncoder = new AppCloudPasswordEncoder() ;
		System.out.println( appCloudPasswordEncoder.encode("123456") );
	}

}
