package lotr.common.entity.item;

import com.mojang.authlib.GameProfile;
import lotr.common.LOTRBannerProtection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class LOTRBannerWhitelistEntry {
	public GameProfile profile;
	public Set<LOTRBannerProtection.Permission> perms = EnumSet.noneOf(LOTRBannerProtection.Permission.class);

	public LOTRBannerWhitelistEntry(GameProfile p) {
		profile = p;
		if (profile == null) {
			throw new IllegalArgumentException("Banner whitelist entry cannot have a null profile!");
		}
	}

	public static List<LOTRBannerProtection.Permission> static_decodePermBitFlags(int i) {
		List<LOTRBannerProtection.Permission> decoded = new ArrayList<>();
		for (LOTRBannerProtection.Permission p : LOTRBannerProtection.Permission.values()) {
			if ((i & p.bitFlag) == 0) {
				continue;
			}
			decoded.add(p);
		}
		return decoded;
	}

	public static int static_encodePermBitFlags(Iterable<LOTRBannerProtection.Permission> permList) {
		int i = 0;
		for (LOTRBannerProtection.Permission p : permList) {
			i |= p.bitFlag;
		}
		return i;
	}

	public void addPermission(LOTRBannerProtection.Permission p) {
		perms.add(p);
	}

	public boolean allowsPermission(LOTRBannerProtection.Permission p) {
		return isPermissionEnabled(LOTRBannerProtection.Permission.FULL) || isPermissionEnabled(p);
	}

	public void clearPermissions() {
		perms.clear();
	}

	public void decodePermBitFlags(int i) {
		setPermissions(static_decodePermBitFlags(i));
	}

	public int encodePermBitFlags() {
		return static_encodePermBitFlags(perms);
	}

	public boolean isPermissionEnabled(LOTRBannerProtection.Permission p) {
		return perms.contains(p);
	}

	public Set<LOTRBannerProtection.Permission> listPermissions() {
		return perms;
	}

	public void removePermission(LOTRBannerProtection.Permission p) {
		perms.remove(p);
	}

	public LOTRBannerWhitelistEntry setFullPerms() {
		clearPermissions();
		addPermission(LOTRBannerProtection.Permission.FULL);
		return this;
	}

	public void setPermissions(Iterable<LOTRBannerProtection.Permission> perms) {
		clearPermissions();
		for (LOTRBannerProtection.Permission p : perms) {
			addPermission(p);
		}
	}
}
