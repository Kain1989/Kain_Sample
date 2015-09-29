package com.telenav.lucene.core.utils.chinalib.district;

import com.telenav.lucene.core.utils.chinalib.district.impl.City;
import com.telenav.lucene.core.utils.chinalib.district.impl.County;
import com.telenav.lucene.core.utils.chinalib.district.impl.State;
import com.telenav.search.commons.util.SearchResourceLoader;
import misc.BatIO;
import org.apache.commons.collections4.CollectionUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DistrictTree {
	// index,accelerate district search
	// including alias
	
	private static final String cnCityTree = "misc/cncity/chinaCityTree.txt";
	private static Map<String, Set<District>> TwoCharMap = new HashMap<String, Set<District>>();

	private static Map<String, Set<District>> namePinyinMap = new HashMap<String, Set<District>>();

	// include alias pinyin
	private static Map<String, Set<District>> fullnamePinyinMap = new HashMap<String, Set<District>>();

	private static Set<State> china = new HashSet<State>();

	// tmp
	public static final Set<String> AS = new TreeSet<String>();
	public static final Map<String, String> tmpMap = new HashMap<String, String>();

	static {
		try {
//			InputStream inputStream = DistrictTree.class.getClassLoader()
//					.getResourceAsStream(
//					        Constants.ONBOARD_SEARCH_RESOURCE_ROOT_PATH + Region.getCurrentRegion().name()
//									+ "/knowledge/chinaCityTree.txt");
			InputStream inputStream = SearchResourceLoader.getResourceAsStream(cnCityTree);
			InputStreamReader srcReader = new InputStreamReader(inputStream,
					"UTF-8");
			init(srcReader);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void init(InputStreamReader srcReader) throws Exception {

		List<String> list = BatIO.read(srcReader);

		State currentState = null;
		County currentCounty = null;

		for (String s : list) {
			CityLineItem it = new CityLineItem(s);
			if (currentState == null
					|| !currentState.getName().equals(it.stateBody)) {
				currentState = new State(it.stateBody, it.state,
						it.stateBodyPinyin, it.statePinyin);
				china.add(currentState);

				putToIndexMap(it.state, currentState);
				putToPinyinMap(currentState);

				// check alias
				if (CollectionUtils.isNotEmpty(it.stateEquals)) {
					Map<String, String> aliasPinyinMap = new LinkedHashMap<String, String>();
					for (int i = 0; i < it.stateEquals.size(); i++) {
						String alias = it.stateEquals.get(i), aliasPY = it.stateEqualsPY
								.get(i);
						aliasPinyinMap.put(alias, aliasPY);

						putToAliasIndexMap(alias, currentState);
						putToAliasPinyinMap(aliasPY, currentState);

						// tmp
						AS.add(alias);
						tmpMap.put(alias, it.county);
					}

					currentState.addAlias(aliasPinyinMap);
				}
			}

			if (it.isState())
				continue;

			if (currentCounty == null
					|| !currentCounty.getFullName().equals(it.county)) {
				currentCounty = new County(it.countyBody, it.county,
						it.countyBodyPinyin, it.countyPinyin, currentState);
				currentState.addSubDist(currentCounty);

				putToIndexMap(it.county, currentCounty);
				putToPinyinMap(currentCounty);

				// check alias
				if (CollectionUtils.isNotEmpty(it.countyEquals)) {
					Map<String, String> aliasPinyinMap = new LinkedHashMap<String, String>();
					for (int i = 0; i < it.countyEquals.size(); i++) {
						String alias = it.countyEquals.get(i), aliasPY = it.countyEqualsPY
								.get(i);
						aliasPinyinMap.put(alias, aliasPY);

						putToAliasIndexMap(alias, currentCounty);
						putToAliasPinyinMap(aliasPY, currentCounty);

						// tmp
						AS.add(alias);
						tmpMap.put(alias, it.county);
					}

					currentCounty.addAlias(aliasPinyinMap);
				}
			}
			if (it.isCity()) {
				City city = new City(it.cityBody, it.city, it.cityBodyPinyin,
						it.cityPinyin, currentCounty);
				currentCounty.addSubDist(city);

				putToIndexMap(it.city, city);
				putToPinyinMap(city);

				// check alias
				if (CollectionUtils.isNotEmpty(it.cityEquals)) {
					Map<String, String> aliasPinyinMap = new LinkedHashMap<String, String>();
					for (int i = 0; i < it.cityEquals.size(); i++) {
						String alias = it.cityEquals.get(i), aliasPY = it.cityEqualsPY
								.get(i);
						aliasPinyinMap.put(alias, aliasPY);

						putToAliasIndexMap(alias, city);
						putToAliasPinyinMap(aliasPY, city);

						// tmp
						AS.add(alias);
						tmpMap.put(alias, it.city);
					}
					city.addAlias(aliasPinyinMap);
				}
			}
		}
	}

	private static void putToIndexMap(String str, District dist) {
		// no city has length smaller than 2
		if (null == str || str.length() < 2)
			return;

		String tag = str.substring(0, 2);
		if (!TwoCharMap.containsKey(tag))
			TwoCharMap.put(tag, new HashSet<District>());
		Set<District> set = TwoCharMap.get(tag);
		set.add(dist);
	}

	private static void putToAliasIndexMap(String alias, District d) {
		// no city has length smaller than 2
		if (null == alias || alias.length() < 2)
			return;

		String tag = alias.substring(0, 2);
		if (!TwoCharMap.containsKey(tag))
			TwoCharMap.put(tag, new HashSet<District>());
		Set<District> set = TwoCharMap.get(tag);
		set.add(d);
	}

	private static void putToPinyinMap(District dist) {
		// no city has length smaller than 2
		if (dist == null)
			return;

		String namePy = dist.getNamePinyin();
		String fullnamePy = dist.getFullNamePinyin();
		Set<District> set = namePinyinMap.get(namePy);
		if (set == null) {
			set = new HashSet<District>();
			namePinyinMap.put(namePy, set);
		}
		set.add(dist);

		set = fullnamePinyinMap.get(fullnamePy);
		if (set == null) {
			set = new HashSet<District>();
			fullnamePinyinMap.put(fullnamePy, set);
		}
		set.add(dist);
	}

	private static void putToAliasPinyinMap(String aliasPinyin, District d) {
		// no city has length smaller than 2
		if (aliasPinyin == null || aliasPinyin.length() < 2)
			return;

		Set<District> set = fullnamePinyinMap.get(aliasPinyin);
		if (set == null) {
			set = new HashSet<District>();
			fullnamePinyinMap.put(aliasPinyin, set);
		}
		set.add(d);
	}

	public static Map<String, Set<District>> getNamePinyinMap() {
		return namePinyinMap;
	}

	public static Map<String, Set<District>> getFullnamePinyinMap() {
		return fullnamePinyinMap;
	}

	public static Set<State> getAllStates() {
		return china;
	}

	public static Map<String, Set<District>> getTwoCharMap() {
		return TwoCharMap;
	}

	public static void main(String[] args) throws Exception {
		for (State st : china) {
			for (District ct : st.getSubDists()) {
				for (District c : ct.getSubDists()) {
					String name = c.getName();
					if (name.endsWith("?") || name.endsWith("?")
							|| name.endsWith("?"))
						System.out.println(c);
				}
			}
		}
	}
}
