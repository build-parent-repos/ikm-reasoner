package dev.ikm.elk.snomed.owlel;

/*-
 * #%L
 * elk-snomed-owl-el
 * %%
 * Copyright (C) 2023 - 2024 Integrated Knowledge Management
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnomedOwlExpressions {

//	public static Stream<String> stream(Path file) throws IOException {
//		// id effectiveTime active moduleId refsetId referencedComponentId owlExpression
//		return Files.lines(file).skip(1).map(line -> line.split("\\t")) //
//				.filter(fields -> Integer.parseInt(fields[2]) == 1) // active
//				.map(fields -> fields[6]);
//	}

	public static List<String> read(Path file) throws IOException {
		// id effectiveTime active moduleId refsetId referencedComponentId owlExpression
		try (Stream<String> st = Files.lines(file)) {
			return st.skip(1).map(line -> line.split("\\t")) //
					.filter(fields -> Integer.parseInt(fields[2]) == 1) // active
					.map(fields -> fields[6]) //
					.collect(Collectors.toCollection(ArrayList::new));
		}
	}

}
