CFG for sharpenH
----------

MiniCFG: UID 121 CFBlock [
	c_s75 = 0
next=122
], Scope = 75
UID 122 CFEndOfMiniCFG [next=102]

UID 102 CFBlock [ miniCFG=121, next=115], Scope = 75

MiniCFG: UID 132 CFBlock [
	t123 = c_s75 < cols_s0 {canonical: (c_s75) < (cols_s0)}
next=134
], Scope = 75
UID 134 CFEndOfMiniCFG [next=115]

UID 115 CFConditional [ miniCFG=132, boolTemp=t123, ifTrue=106, ifFalse=116], Scope = 75

MiniCFG: UID 139 CFBlock [
	r_s75 = 0
next=140
], Scope = 76
UID 140 CFEndOfMiniCFG [next=106]

UID 106 CFBlock [ miniCFG=139, next=114], Scope = 76

MiniCFG: UID 150 CFBlock [
	t141 = r_s75 < rows_s0 {canonical: (r_s75) < (rows_s0)}
next=152
], Scope = 76
UID 152 CFEndOfMiniCFG [next=114]

UID 114 CFConditional [ miniCFG=150, boolTemp=t141, ifTrue=109, ifFalse=103], Scope = 76

MiniCFG: UID 279 CFBlock [
	t158 = 2193
	t156 = r_s75 * t158 {canonical: (r_s75) * (2193)}
	t170 = 3
	t168 = c_s75 * t170 {canonical: (c_s75) * (3)}
	t155 = t156 + t168 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	t192 = 2193
	t190 = r_s75 * t192 {canonical: (r_s75) * (2193)}
	t204 = 3
	t202 = c_s75 * t204 {canonical: (c_s75) * (3)}
	t189 = t190 + t202 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	t188 = image_s0[t189]
	t231 = 731
	t229 = r_s75 * t231 {canonical: (r_s75) * (731)}
	t228 = t229 + c_s75 {canonical: ((r_s75) * (731)) + (c_s75)}
	t227 = unsharpMask_s0[t228]
	t225 = amount_s74 * t227 {canonical: (amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)])}
	t223 = channelOne_s74 + t225 {canonical: (channelOne_s74) + ((amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)]))}
	t187 = t188 * t223 {canonical: (image_s0[((r_s75) * (2193)) + ((c_s75) * (3))]) * ((channelOne_s74) + ((amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)])))}
	image_s0[t155] = t187 / channelOne_s74 {canonical: ((image_s0[((r_s75) * (2193)) + ((c_s75) * (3))]) * ((channelOne_s74) + ((amount_s74) * (unsharpMask_s0[((r_s75) * (731)) + (c_s75)])))) / (channelOne_s74)}
next=280
], Scope = 77
UID 280 CFEndOfMiniCFG [next=109]

UID 109 CFBlock [ miniCFG=279, next=113], Scope = 77

MiniCFG: UID 324 CFBlock [
	t286 = 2193
	t284 = r_s75 * t286 {canonical: (r_s75) * (2193)}
	t298 = 3
	t296 = c_s75 * t298 {canonical: (c_s75) * (3)}
	t283 = t284 + t296 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	t282 = image_s0[t283]
	t281 = t282 >= channelOne_s74 {canonical: (image_s0[((r_s75) * (2193)) + ((c_s75) * (3))]) >= (channelOne_s74)}
next=326
], Scope = 77
UID 326 CFEndOfMiniCFG [next=113]

UID 113 CFConditional [ miniCFG=324, boolTemp=t281, ifTrue=112, ifFalse=107], Scope = 77

MiniCFG: UID 371 CFBlock [
	t332 = 2193
	t330 = r_s75 * t332 {canonical: (r_s75) * (2193)}
	t344 = 3
	t342 = c_s75 * t344 {canonical: (c_s75) * (3)}
	t329 = t330 + t342 {canonical: ((r_s75) * (2193)) + ((c_s75) * (3))}
	t362 = 1
	image_s0[t329] = channelOne_s74 - t362 {canonical: (channelOne_s74) - (1)}
next=372
], Scope = 78
UID 372 CFEndOfMiniCFG [next=112]

UID 112 CFBlock [ miniCFG=371, next=107], Scope = 78

MiniCFG: UID 380 CFBlock [
	t376 = 1
	r_s75 += t376
next=381
], Scope = 76
UID 381 CFEndOfMiniCFG [next=107]

UID 107 CFBlock [ miniCFG=380, next=114], Scope = 76

MiniCFG: UID 389 CFBlock [
	t385 = 1
	c_s75 += t385
next=390
], Scope = 75
UID 390 CFEndOfMiniCFG [next=103]

UID 103 CFBlock [ miniCFG=389, next=115], Scope = 75
UID 116 CFReturn
----------
CFG for createUnsharpMaskV
----------

MiniCFG: UID 473 CFBlock [
	center_s65 = 3
	r_s65 = 0
next=474
], Scope = 65
UID 474 CFEndOfMiniCFG [next=396]

UID 396 CFBlock [ miniCFG=473, next=425], Scope = 65

MiniCFG: UID 484 CFBlock [
	t475 = r_s65 < rows_s0 {canonical: (r_s65) < (rows_s0)}
next=486
], Scope = 65
UID 486 CFEndOfMiniCFG [next=425]

UID 425 CFConditional [ miniCFG=484, boolTemp=t475, ifTrue=400, ifFalse=428], Scope = 65

MiniCFG: UID 491 CFBlock [
	c_s65 = 0
next=492
], Scope = 66
UID 492 CFEndOfMiniCFG [next=400]

UID 400 CFBlock [ miniCFG=491, next=404], Scope = 66

MiniCFG: UID 502 CFBlock [
	t493 = c_s65 < center_s65 {canonical: (c_s65) < (center_s65)}
next=504
], Scope = 66
UID 504 CFEndOfMiniCFG [next=404]

UID 404 CFConditional [ miniCFG=502, boolTemp=t493, ifTrue=403, ifFalse=406], Scope = 66

MiniCFG: UID 572 CFBlock [
	t510 = 731
	t508 = r_s65 * t510 {canonical: (r_s65) * (731)}
	t507 = t508 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t532 = 2193
	t530 = r_s65 * t532 {canonical: (r_s65) * (2193)}
	t544 = 3
	t542 = c_s65 * t544 {canonical: (c_s65) * (3)}
	t529 = t530 + t542 {canonical: ((r_s65) * (2193)) + ((c_s65) * (3))}
	t561 = 2
	t528 = t529 + t561 {canonical: (((r_s65) * (2193)) + ((c_s65) * (3))) + (2)}
	unsharpMask_s0[t507] = image_s0[t528]
next=573
], Scope = 67
UID 573 CFEndOfMiniCFG [next=403]

UID 403 CFBlock [ miniCFG=572, next=401], Scope = 67

MiniCFG: UID 581 CFBlock [
	t577 = 1
	c_s65 += t577
next=582
], Scope = 66
UID 582 CFEndOfMiniCFG [next=401]

UID 401 CFBlock [ miniCFG=581, next=404], Scope = 66

MiniCFG: UID 588 CFBlock [
	c_s65 = center_s65 {canonical: center_s65}
next=589
], Scope = 66
UID 589 CFEndOfMiniCFG [next=406]

UID 406 CFBlock [ miniCFG=588, next=418], Scope = 66

MiniCFG: UID 608 CFBlock [
	t592 = cols_s0 - center_s65 {canonical: (cols_s0) - (center_s65)}
	t590 = c_s65 < t592 {canonical: (c_s65) < ((cols_s0) - (center_s65))}
next=610
], Scope = 66
UID 610 CFEndOfMiniCFG [next=418]

UID 418 CFConditional [ miniCFG=608, boolTemp=t590, ifTrue=417, ifFalse=420], Scope = 66

MiniCFG: UID 1269 CFBlock [
	t616 = 731
	t614 = r_s65 * t616 {canonical: (r_s65) * (731)}
	t613 = t614 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	unsharpMask_s0[t613] = 0
	t639 = 731
	t637 = r_s65 * t639 {canonical: (r_s65) * (731)}
	t636 = t637 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t663 = 2193
	t661 = r_s65 * t663 {canonical: (r_s65) * (2193)}
	t674 = 3
	t673 = t674 * c_s65 {canonical: (3) * (c_s65)}
	t660 = t661 + t673 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	t692 = 7
	t659 = t660 - t692 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) - (7)}
	t658 = image_s0[t659]
	t705 = 0
	t704 = unsharpKernel_s0[t705]
	t657 = t658 * t704 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) - (7)]) * (unsharpKernel_s0[0])}
	unsharpMask_s0[t636] += t657
	t722 = 731
	t720 = r_s65 * t722 {canonical: (r_s65) * (731)}
	t719 = t720 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t746 = 2193
	t744 = r_s65 * t746 {canonical: (r_s65) * (2193)}
	t757 = 3
	t756 = t757 * c_s65 {canonical: (3) * (c_s65)}
	t743 = t744 + t756 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	t775 = 4
	t742 = t743 - t775 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) - (4)}
	t741 = image_s0[t742]
	t788 = 1
	t787 = unsharpKernel_s0[t788]
	t740 = t741 * t787 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) - (4)]) * (unsharpKernel_s0[1])}
	unsharpMask_s0[t719] += t740
	t805 = 731
	t803 = r_s65 * t805 {canonical: (r_s65) * (731)}
	t802 = t803 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t829 = 2193
	t827 = r_s65 * t829 {canonical: (r_s65) * (2193)}
	t840 = 3
	t839 = t840 * c_s65 {canonical: (3) * (c_s65)}
	t826 = t827 + t839 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	t858 = 1
	t825 = t826 - t858 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) - (1)}
	t824 = image_s0[t825]
	t871 = 2
	t870 = unsharpKernel_s0[t871]
	t823 = t824 * t870 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) - (1)]) * (unsharpKernel_s0[2])}
	unsharpMask_s0[t802] += t823
	t888 = 731
	t886 = r_s65 * t888 {canonical: (r_s65) * (731)}
	t885 = t886 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t912 = 2193
	t910 = r_s65 * t912 {canonical: (r_s65) * (2193)}
	t923 = 3
	t922 = t923 * c_s65 {canonical: (3) * (c_s65)}
	t909 = t910 + t922 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	t941 = 2
	t908 = t909 + t941 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (2)}
	t907 = image_s0[t908]
	t954 = 3
	t953 = unsharpKernel_s0[t954]
	t906 = t907 * t953 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (2)]) * (unsharpKernel_s0[3])}
	unsharpMask_s0[t885] += t906
	t971 = 731
	t969 = r_s65 * t971 {canonical: (r_s65) * (731)}
	t968 = t969 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t995 = 2193
	t993 = r_s65 * t995 {canonical: (r_s65) * (2193)}
	t1006 = 3
	t1005 = t1006 * c_s65 {canonical: (3) * (c_s65)}
	t992 = t993 + t1005 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	t1024 = 5
	t991 = t992 + t1024 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (5)}
	t990 = image_s0[t991]
	t1037 = 4
	t1036 = unsharpKernel_s0[t1037]
	t989 = t990 * t1036 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (5)]) * (unsharpKernel_s0[4])}
	unsharpMask_s0[t968] += t989
	t1054 = 731
	t1052 = r_s65 * t1054 {canonical: (r_s65) * (731)}
	t1051 = t1052 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1078 = 2193
	t1076 = r_s65 * t1078 {canonical: (r_s65) * (2193)}
	t1089 = 3
	t1088 = t1089 * c_s65 {canonical: (3) * (c_s65)}
	t1075 = t1076 + t1088 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	t1107 = 8
	t1074 = t1075 + t1107 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (8)}
	t1073 = image_s0[t1074]
	t1120 = 5
	t1119 = unsharpKernel_s0[t1120]
	t1072 = t1073 * t1119 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (8)]) * (unsharpKernel_s0[5])}
	unsharpMask_s0[t1051] += t1072
	t1137 = 731
	t1135 = r_s65 * t1137 {canonical: (r_s65) * (731)}
	t1134 = t1135 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1161 = 2193
	t1159 = r_s65 * t1161 {canonical: (r_s65) * (2193)}
	t1172 = 3
	t1171 = t1172 * c_s65 {canonical: (3) * (c_s65)}
	t1158 = t1159 + t1171 {canonical: ((r_s65) * (2193)) + ((3) * (c_s65))}
	t1190 = 11
	t1157 = t1158 + t1190 {canonical: (((r_s65) * (2193)) + ((3) * (c_s65))) + (11)}
	t1156 = image_s0[t1157]
	t1203 = 6
	t1202 = unsharpKernel_s0[t1203]
	t1155 = t1156 * t1202 {canonical: (image_s0[(((r_s65) * (2193)) + ((3) * (c_s65))) + (11)]) * (unsharpKernel_s0[6])}
	unsharpMask_s0[t1134] += t1155
	t1220 = 731
	t1218 = r_s65 * t1220 {canonical: (r_s65) * (731)}
	t1217 = t1218 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1242 = 731
	t1240 = r_s65 * t1242 {canonical: (r_s65) * (731)}
	t1239 = t1240 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1238 = unsharpMask_s0[t1239]
	unsharpMask_s0[t1217] = t1238 / kernel_sum_s0 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) / (kernel_sum_s0)}
next=1270
], Scope = 68
UID 1270 CFEndOfMiniCFG [next=417]

UID 417 CFBlock [ miniCFG=1269, next=407], Scope = 68

MiniCFG: UID 1278 CFBlock [
	t1274 = 1
	c_s65 += t1274
next=1279
], Scope = 66
UID 1279 CFEndOfMiniCFG [next=407]

UID 407 CFBlock [ miniCFG=1278, next=418], Scope = 66

MiniCFG: UID 1291 CFBlock [
	c_s65 = cols_s0 - center_s65 {canonical: (cols_s0) - (center_s65)}
next=1292
], Scope = 66
UID 1292 CFEndOfMiniCFG [next=420]

UID 420 CFBlock [ miniCFG=1291, next=424], Scope = 66

MiniCFG: UID 1302 CFBlock [
	t1293 = c_s65 < cols_s0 {canonical: (c_s65) < (cols_s0)}
next=1304
], Scope = 66
UID 1304 CFEndOfMiniCFG [next=424]

UID 424 CFConditional [ miniCFG=1302, boolTemp=t1293, ifTrue=423, ifFalse=397], Scope = 66

MiniCFG: UID 1372 CFBlock [
	t1310 = 731
	t1308 = r_s65 * t1310 {canonical: (r_s65) * (731)}
	t1307 = t1308 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1332 = 2193
	t1330 = r_s65 * t1332 {canonical: (r_s65) * (2193)}
	t1344 = 3
	t1342 = c_s65 * t1344 {canonical: (c_s65) * (3)}
	t1329 = t1330 + t1342 {canonical: ((r_s65) * (2193)) + ((c_s65) * (3))}
	t1361 = 2
	t1328 = t1329 + t1361 {canonical: (((r_s65) * (2193)) + ((c_s65) * (3))) + (2)}
	unsharpMask_s0[t1307] = image_s0[t1328]
next=1373
], Scope = 69
UID 1373 CFEndOfMiniCFG [next=423]

UID 423 CFBlock [ miniCFG=1372, next=421], Scope = 69

MiniCFG: UID 1381 CFBlock [
	t1377 = 1
	c_s65 += t1377
next=1382
], Scope = 66
UID 1382 CFEndOfMiniCFG [next=421]

UID 421 CFBlock [ miniCFG=1381, next=424], Scope = 66

MiniCFG: UID 1390 CFBlock [
	t1386 = 1
	r_s65 += t1386
next=1391
], Scope = 65
UID 1391 CFEndOfMiniCFG [next=397]

UID 397 CFBlock [ miniCFG=1390, next=425], Scope = 65

MiniCFG: UID 1415 CFBlock [
	t1394 = center_s65 {canonical: center_s65}
	t1399 = center_s65 {canonical: center_s65}
	t1398 = unsharpKernel_s0[t1399]
	unsharpKernel_s0[t1394] = t1398 - kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s65]) - (kernel_sum_s0)}
	c_s65 = 0
next=1416
], Scope = 65
UID 1416 CFEndOfMiniCFG [next=428]

UID 428 CFBlock [ miniCFG=1415, next=463], Scope = 65

MiniCFG: UID 1426 CFBlock [
	t1417 = c_s65 < cols_s0 {canonical: (c_s65) < (cols_s0)}
next=1428
], Scope = 65
UID 1428 CFEndOfMiniCFG [next=463]

UID 463 CFConditional [ miniCFG=1426, boolTemp=t1417, ifTrue=435, ifFalse=464], Scope = 65

MiniCFG: UID 1470 CFBlock [
	t1432 = c_s65 {canonical: c_s65}
	m1_s70 = unsharpMask_s0[t1432]
	t1441 = 731
	t1439 = c_s65 + t1441 {canonical: (c_s65) + (731)}
	m2_s70 = unsharpMask_s0[t1439]
	t1456 = 1462
	t1454 = c_s65 + t1456 {canonical: (c_s65) + (1462)}
	m3_s70 = unsharpMask_s0[t1454]
	r_s65 = 0
next=1471
], Scope = 70
UID 1471 CFEndOfMiniCFG [next=435]

UID 435 CFBlock [ miniCFG=1470, next=439], Scope = 70

MiniCFG: UID 1481 CFBlock [
	t1472 = r_s65 < center_s65 {canonical: (r_s65) < (center_s65)}
next=1483
], Scope = 70
UID 1483 CFEndOfMiniCFG [next=439]

UID 439 CFConditional [ miniCFG=1481, boolTemp=t1472, ifTrue=438, ifFalse=441], Scope = 70

MiniCFG: UID 1508 CFBlock [
	t1489 = 731
	t1487 = r_s65 * t1489 {canonical: (r_s65) * (731)}
	t1486 = t1487 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	unsharpMask_s0[t1486] = 0
next=1509
], Scope = 71
UID 1509 CFEndOfMiniCFG [next=438]

UID 438 CFBlock [ miniCFG=1508, next=436], Scope = 71

MiniCFG: UID 1517 CFBlock [
	t1513 = 1
	r_s65 += t1513
next=1518
], Scope = 70
UID 1518 CFEndOfMiniCFG [next=436]

UID 436 CFBlock [ miniCFG=1517, next=439], Scope = 70

MiniCFG: UID 1524 CFBlock [
	r_s65 = center_s65 {canonical: center_s65}
next=1525
], Scope = 70
UID 1525 CFEndOfMiniCFG [next=441]

UID 441 CFBlock [ miniCFG=1524, next=456], Scope = 70

MiniCFG: UID 1544 CFBlock [
	t1528 = rows_s0 - center_s65 {canonical: (rows_s0) - (center_s65)}
	t1526 = r_s65 < t1528 {canonical: (r_s65) < ((rows_s0) - (center_s65))}
next=1546
], Scope = 70
UID 1546 CFEndOfMiniCFG [next=456]

UID 456 CFConditional [ miniCFG=1544, boolTemp=t1526, ifTrue=455, ifFalse=458], Scope = 70

MiniCFG: UID 2088 CFBlock [
	dot_s72 = 0
	t1558 = 731
	t1556 = r_s65 * t1558 {canonical: (r_s65) * (731)}
	t1555 = t1556 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1554 = unsharpMask_s0[t1555]
	t1581 = 0
	t1580 = unsharpKernel_s0[t1581]
	t1578 = m1_s70 * t1580 {canonical: (m1_s70) * (unsharpKernel_s0[0])}
	t1553 = t1554 + t1578 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((m1_s70) * (unsharpKernel_s0[0]))}
	dot_s72 += t1553
	t1608 = 731
	t1606 = r_s65 * t1608 {canonical: (r_s65) * (731)}
	t1605 = t1606 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1604 = unsharpMask_s0[t1605]
	t1631 = 1
	t1630 = unsharpKernel_s0[t1631]
	t1628 = m2_s70 * t1630 {canonical: (m2_s70) * (unsharpKernel_s0[1])}
	t1603 = t1604 + t1628 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((m2_s70) * (unsharpKernel_s0[1]))}
	dot_s72 += t1603
	t1658 = 731
	t1656 = r_s65 * t1658 {canonical: (r_s65) * (731)}
	t1655 = t1656 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1654 = unsharpMask_s0[t1655]
	t1681 = 2
	t1680 = unsharpKernel_s0[t1681]
	t1678 = m3_s70 * t1680 {canonical: (m3_s70) * (unsharpKernel_s0[2])}
	t1653 = t1654 + t1678 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((m3_s70) * (unsharpKernel_s0[2]))}
	dot_s72 += t1653
	t1708 = 731
	t1706 = r_s65 * t1708 {canonical: (r_s65) * (731)}
	t1705 = t1706 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1704 = unsharpMask_s0[t1705]
	t1732 = 731
	t1731 = t1732 * r_s65 {canonical: (731) * (r_s65)}
	t1730 = t1731 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	t1729 = unsharpMask_s0[t1730]
	t1754 = 3
	t1753 = unsharpKernel_s0[t1754]
	t1728 = t1729 * t1753 {canonical: (unsharpMask_s0[((731) * (r_s65)) + (c_s65)]) * (unsharpKernel_s0[3])}
	t1703 = t1704 + t1728 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[((731) * (r_s65)) + (c_s65)]) * (unsharpKernel_s0[3]))}
	dot_s72 += t1703
	t1781 = 731
	t1779 = r_s65 * t1781 {canonical: (r_s65) * (731)}
	t1778 = t1779 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1777 = unsharpMask_s0[t1778]
	t1806 = 731
	t1805 = t1806 * r_s65 {canonical: (731) * (r_s65)}
	t1804 = t1805 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	t1825 = 731
	t1803 = t1804 + t1825 {canonical: (((731) * (r_s65)) + (c_s65)) + (731)}
	t1802 = unsharpMask_s0[t1803]
	t1838 = 4
	t1837 = unsharpKernel_s0[t1838]
	t1801 = t1802 * t1837 {canonical: (unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (731)]) * (unsharpKernel_s0[4])}
	t1776 = t1777 + t1801 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (731)]) * (unsharpKernel_s0[4]))}
	dot_s72 += t1776
	t1865 = 731
	t1863 = r_s65 * t1865 {canonical: (r_s65) * (731)}
	t1862 = t1863 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1861 = unsharpMask_s0[t1862]
	t1890 = 731
	t1889 = t1890 * r_s65 {canonical: (731) * (r_s65)}
	t1888 = t1889 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	t1909 = 1462
	t1887 = t1888 + t1909 {canonical: (((731) * (r_s65)) + (c_s65)) + (1462)}
	t1886 = unsharpMask_s0[t1887]
	t1922 = 5
	t1921 = unsharpKernel_s0[t1922]
	t1885 = t1886 * t1921 {canonical: (unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (1462)]) * (unsharpKernel_s0[5])}
	t1860 = t1861 + t1885 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (1462)]) * (unsharpKernel_s0[5]))}
	dot_s72 += t1860
	t1949 = 731
	t1947 = r_s65 * t1949 {canonical: (r_s65) * (731)}
	t1946 = t1947 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	t1945 = unsharpMask_s0[t1946]
	t1974 = 731
	t1973 = t1974 * r_s65 {canonical: (731) * (r_s65)}
	t1972 = t1973 + c_s65 {canonical: ((731) * (r_s65)) + (c_s65)}
	t1993 = 2193
	t1971 = t1972 + t1993 {canonical: (((731) * (r_s65)) + (c_s65)) + (2193)}
	t1970 = unsharpMask_s0[t1971]
	t2006 = 6
	t2005 = unsharpKernel_s0[t2006]
	t1969 = t1970 * t2005 {canonical: (unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (2193)]) * (unsharpKernel_s0[6])}
	t1944 = t1945 + t1969 {canonical: (unsharpMask_s0[((r_s65) * (731)) + (c_s65)]) + ((unsharpMask_s0[(((731) * (r_s65)) + (c_s65)) + (2193)]) * (unsharpKernel_s0[6]))}
	dot_s72 += t1944
	m1_s70 = m2_s70 {canonical: m2_s70}
	m2_s70 = m3_s70 {canonical: m3_s70}
	t2039 = 731
	t2037 = r_s65 * t2039 {canonical: (r_s65) * (731)}
	t2036 = t2037 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	m3_s70 = unsharpMask_s0[t2036]
	t2062 = 731
	t2060 = r_s65 * t2062 {canonical: (r_s65) * (731)}
	t2059 = t2060 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	unsharpMask_s0[t2059] = dot_s72 / kernel_sum_s0 {canonical: (dot_s72) / (kernel_sum_s0)}
next=2089
], Scope = 72
UID 2089 CFEndOfMiniCFG [next=455]

UID 455 CFBlock [ miniCFG=2088, next=442], Scope = 72

MiniCFG: UID 2097 CFBlock [
	t2093 = 1
	r_s65 += t2093
next=2098
], Scope = 70
UID 2098 CFEndOfMiniCFG [next=442]

UID 442 CFBlock [ miniCFG=2097, next=456], Scope = 70

MiniCFG: UID 2110 CFBlock [
	r_s65 = rows_s0 - center_s65 {canonical: (rows_s0) - (center_s65)}
next=2111
], Scope = 70
UID 2111 CFEndOfMiniCFG [next=458]

UID 458 CFBlock [ miniCFG=2110, next=462], Scope = 70

MiniCFG: UID 2121 CFBlock [
	t2112 = r_s65 < rows_s0 {canonical: (r_s65) < (rows_s0)}
next=2123
], Scope = 70
UID 2123 CFEndOfMiniCFG [next=462]

UID 462 CFConditional [ miniCFG=2121, boolTemp=t2112, ifTrue=461, ifFalse=429], Scope = 70

MiniCFG: UID 2148 CFBlock [
	t2129 = 731
	t2127 = r_s65 * t2129 {canonical: (r_s65) * (731)}
	t2126 = t2127 + c_s65 {canonical: ((r_s65) * (731)) + (c_s65)}
	unsharpMask_s0[t2126] = 0
next=2149
], Scope = 73
UID 2149 CFEndOfMiniCFG [next=461]

UID 461 CFBlock [ miniCFG=2148, next=459], Scope = 73

MiniCFG: UID 2157 CFBlock [
	t2153 = 1
	r_s65 += t2153
next=2158
], Scope = 70
UID 2158 CFEndOfMiniCFG [next=459]

UID 459 CFBlock [ miniCFG=2157, next=462], Scope = 70

MiniCFG: UID 2166 CFBlock [
	t2162 = 1
	c_s65 += t2162
next=2167
], Scope = 65
UID 2167 CFEndOfMiniCFG [next=429]

UID 429 CFBlock [ miniCFG=2166, next=463], Scope = 65

MiniCFG: UID 2188 CFBlock [
	t2170 = center_s65 {canonical: center_s65}
	t2175 = center_s65 {canonical: center_s65}
	t2174 = unsharpKernel_s0[t2175]
	unsharpKernel_s0[t2170] = t2174 + kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s65]) + (kernel_sum_s0)}
next=2189
], Scope = 65
UID 2189 CFEndOfMiniCFG [next=464]

UID 464 CFBlock [ miniCFG=2188, next=465], Scope = 65
UID 465 CFReturn
----------
CFG for createKernel
----------

MiniCFG: UID 2254 CFBlock [
	t2211 = 0
	unsharpKernel_s0[t2211] = 4433
	t2216 = 1
	unsharpKernel_s0[t2216] = 54006
	t2221 = 2
	unsharpKernel_s0[t2221] = 242036
	t2226 = 3
	unsharpKernel_s0[t2226] = 399050
	t2231 = 4
	unsharpKernel_s0[t2231] = 242036
	t2236 = 5
	unsharpKernel_s0[t2236] = 54006
	t2241 = 6
	unsharpKernel_s0[t2241] = 4433
	center_s42 = 3
	kernel_sum_s0 = 0
	i_s42 = 0
next=2255
], Scope = 42
UID 2255 CFEndOfMiniCFG [next=2203]

UID 2203 CFBlock [ miniCFG=2254, next=2207], Scope = 42

MiniCFG: UID 2287 CFBlock [
	t2261 = 2
	t2259 = center_s42 * t2261 {canonical: (center_s42) * (2)}
	t2271 = 1
	t2258 = t2259 + t2271 {canonical: ((center_s42) * (2)) + (1)}
	t2256 = i_s42 < t2258 {canonical: (i_s42) < (((center_s42) * (2)) + (1))}
next=2289
], Scope = 42
UID 2289 CFEndOfMiniCFG [next=2207]

UID 2207 CFConditional [ miniCFG=2287, boolTemp=t2256, ifTrue=2206, ifFalse=2208], Scope = 42

MiniCFG: UID 2307 CFBlock [
	t2295 = i_s42 {canonical: i_s42}
	t2294 = unsharpKernel_s0[t2295]
	kernel_sum_s0 = kernel_sum_s0 + t2294 {canonical: (kernel_sum_s0) + (unsharpKernel_s0[i_s42])}
next=2308
], Scope = 43
UID 2308 CFEndOfMiniCFG [next=2206]

UID 2206 CFBlock [ miniCFG=2307, next=2204], Scope = 43

MiniCFG: UID 2316 CFBlock [
	t2312 = 1
	i_s42 += t2312
next=2317
], Scope = 42
UID 2317 CFEndOfMiniCFG [next=2204]

UID 2204 CFBlock [ miniCFG=2316, next=2207], Scope = 42
UID 2208 CFReturn
----------
CFG for main
----------

MiniCFG: UID 2406 CFBlock [
	read_file_s0[]
	start_caliper_s0[]
	levels_s0[]
	convert2HSV_s0[]
	createKernel_s0[]
	createUnsharpMaskH_s0[]
	t2358 = 4
	t2357 = -t2358
	t2363 = 360
	sharpenH_s0[t2357, t2363]
	createUnsharpMaskS_s0[]
	t2373 = 4
	t2372 = -t2373
	t2378 = 1024
	sharpenS_s0[t2372, t2378]
	createUnsharpMaskV_s0[]
	t2388 = 4
	t2387 = -t2388
	t2393 = 1024
	sharpenV_s0[t2387, t2393]
	convert2RGB_s0[]
	end_caliper_s0[]
	write_file_s0[]
next=2407
], Scope = 103
UID 2407 CFEndOfMiniCFG [next=2334]

UID 2334 CFBlock [ miniCFG=2406, next=2335], Scope = 103
UID 2335 CFReturn
----------
CFG for createUnsharpMaskS
----------

MiniCFG: UID 2490 CFBlock [
	center_s55 = 3
	r_s55 = 0
next=2491
], Scope = 55
UID 2491 CFEndOfMiniCFG [next=2413]

UID 2413 CFBlock [ miniCFG=2490, next=2442], Scope = 55

MiniCFG: UID 2501 CFBlock [
	t2492 = r_s55 < rows_s0 {canonical: (r_s55) < (rows_s0)}
next=2503
], Scope = 55
UID 2503 CFEndOfMiniCFG [next=2442]

UID 2442 CFConditional [ miniCFG=2501, boolTemp=t2492, ifTrue=2417, ifFalse=2445], Scope = 55

MiniCFG: UID 2508 CFBlock [
	c_s55 = 0
next=2509
], Scope = 56
UID 2509 CFEndOfMiniCFG [next=2417]

UID 2417 CFBlock [ miniCFG=2508, next=2421], Scope = 56

MiniCFG: UID 2519 CFBlock [
	t2510 = c_s55 < center_s55 {canonical: (c_s55) < (center_s55)}
next=2521
], Scope = 56
UID 2521 CFEndOfMiniCFG [next=2421]

UID 2421 CFConditional [ miniCFG=2519, boolTemp=t2510, ifTrue=2420, ifFalse=2423], Scope = 56

MiniCFG: UID 2589 CFBlock [
	t2527 = 731
	t2525 = r_s55 * t2527 {canonical: (r_s55) * (731)}
	t2524 = t2525 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t2549 = 2193
	t2547 = r_s55 * t2549 {canonical: (r_s55) * (2193)}
	t2561 = 3
	t2559 = c_s55 * t2561 {canonical: (c_s55) * (3)}
	t2546 = t2547 + t2559 {canonical: ((r_s55) * (2193)) + ((c_s55) * (3))}
	t2578 = 1
	t2545 = t2546 + t2578 {canonical: (((r_s55) * (2193)) + ((c_s55) * (3))) + (1)}
	unsharpMask_s0[t2524] = image_s0[t2545]
next=2590
], Scope = 57
UID 2590 CFEndOfMiniCFG [next=2420]

UID 2420 CFBlock [ miniCFG=2589, next=2418], Scope = 57

MiniCFG: UID 2598 CFBlock [
	t2594 = 1
	c_s55 += t2594
next=2599
], Scope = 56
UID 2599 CFEndOfMiniCFG [next=2418]

UID 2418 CFBlock [ miniCFG=2598, next=2421], Scope = 56

MiniCFG: UID 2605 CFBlock [
	c_s55 = center_s55 {canonical: center_s55}
next=2606
], Scope = 56
UID 2606 CFEndOfMiniCFG [next=2423]

UID 2423 CFBlock [ miniCFG=2605, next=2435], Scope = 56

MiniCFG: UID 2625 CFBlock [
	t2609 = cols_s0 - center_s55 {canonical: (cols_s0) - (center_s55)}
	t2607 = c_s55 < t2609 {canonical: (c_s55) < ((cols_s0) - (center_s55))}
next=2627
], Scope = 56
UID 2627 CFEndOfMiniCFG [next=2435]

UID 2435 CFConditional [ miniCFG=2625, boolTemp=t2607, ifTrue=2434, ifFalse=2437], Scope = 56

MiniCFG: UID 3286 CFBlock [
	t2633 = 731
	t2631 = r_s55 * t2633 {canonical: (r_s55) * (731)}
	t2630 = t2631 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	unsharpMask_s0[t2630] = 0
	t2656 = 731
	t2654 = r_s55 * t2656 {canonical: (r_s55) * (731)}
	t2653 = t2654 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t2680 = 2193
	t2678 = r_s55 * t2680 {canonical: (r_s55) * (2193)}
	t2691 = 3
	t2690 = t2691 * c_s55 {canonical: (3) * (c_s55)}
	t2677 = t2678 + t2690 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	t2709 = 8
	t2676 = t2677 - t2709 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) - (8)}
	t2675 = image_s0[t2676]
	t2722 = 0
	t2721 = unsharpKernel_s0[t2722]
	t2674 = t2675 * t2721 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) - (8)]) * (unsharpKernel_s0[0])}
	unsharpMask_s0[t2653] += t2674
	t2739 = 731
	t2737 = r_s55 * t2739 {canonical: (r_s55) * (731)}
	t2736 = t2737 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t2763 = 2193
	t2761 = r_s55 * t2763 {canonical: (r_s55) * (2193)}
	t2774 = 3
	t2773 = t2774 * c_s55 {canonical: (3) * (c_s55)}
	t2760 = t2761 + t2773 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	t2792 = 5
	t2759 = t2760 - t2792 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) - (5)}
	t2758 = image_s0[t2759]
	t2805 = 1
	t2804 = unsharpKernel_s0[t2805]
	t2757 = t2758 * t2804 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) - (5)]) * (unsharpKernel_s0[1])}
	unsharpMask_s0[t2736] += t2757
	t2822 = 731
	t2820 = r_s55 * t2822 {canonical: (r_s55) * (731)}
	t2819 = t2820 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t2846 = 2193
	t2844 = r_s55 * t2846 {canonical: (r_s55) * (2193)}
	t2857 = 3
	t2856 = t2857 * c_s55 {canonical: (3) * (c_s55)}
	t2843 = t2844 + t2856 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	t2875 = 2
	t2842 = t2843 - t2875 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) - (2)}
	t2841 = image_s0[t2842]
	t2888 = 2
	t2887 = unsharpKernel_s0[t2888]
	t2840 = t2841 * t2887 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) - (2)]) * (unsharpKernel_s0[2])}
	unsharpMask_s0[t2819] += t2840
	t2905 = 731
	t2903 = r_s55 * t2905 {canonical: (r_s55) * (731)}
	t2902 = t2903 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t2929 = 2193
	t2927 = r_s55 * t2929 {canonical: (r_s55) * (2193)}
	t2940 = 3
	t2939 = t2940 * c_s55 {canonical: (3) * (c_s55)}
	t2926 = t2927 + t2939 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	t2958 = 1
	t2925 = t2926 + t2958 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (1)}
	t2924 = image_s0[t2925]
	t2971 = 3
	t2970 = unsharpKernel_s0[t2971]
	t2923 = t2924 * t2970 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (1)]) * (unsharpKernel_s0[3])}
	unsharpMask_s0[t2902] += t2923
	t2988 = 731
	t2986 = r_s55 * t2988 {canonical: (r_s55) * (731)}
	t2985 = t2986 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3012 = 2193
	t3010 = r_s55 * t3012 {canonical: (r_s55) * (2193)}
	t3023 = 3
	t3022 = t3023 * c_s55 {canonical: (3) * (c_s55)}
	t3009 = t3010 + t3022 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	t3041 = 4
	t3008 = t3009 + t3041 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (4)}
	t3007 = image_s0[t3008]
	t3054 = 4
	t3053 = unsharpKernel_s0[t3054]
	t3006 = t3007 * t3053 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (4)]) * (unsharpKernel_s0[4])}
	unsharpMask_s0[t2985] += t3006
	t3071 = 731
	t3069 = r_s55 * t3071 {canonical: (r_s55) * (731)}
	t3068 = t3069 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3095 = 2193
	t3093 = r_s55 * t3095 {canonical: (r_s55) * (2193)}
	t3106 = 3
	t3105 = t3106 * c_s55 {canonical: (3) * (c_s55)}
	t3092 = t3093 + t3105 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	t3124 = 7
	t3091 = t3092 + t3124 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (7)}
	t3090 = image_s0[t3091]
	t3137 = 5
	t3136 = unsharpKernel_s0[t3137]
	t3089 = t3090 * t3136 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (7)]) * (unsharpKernel_s0[5])}
	unsharpMask_s0[t3068] += t3089
	t3154 = 731
	t3152 = r_s55 * t3154 {canonical: (r_s55) * (731)}
	t3151 = t3152 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3178 = 2193
	t3176 = r_s55 * t3178 {canonical: (r_s55) * (2193)}
	t3189 = 3
	t3188 = t3189 * c_s55 {canonical: (3) * (c_s55)}
	t3175 = t3176 + t3188 {canonical: ((r_s55) * (2193)) + ((3) * (c_s55))}
	t3207 = 10
	t3174 = t3175 + t3207 {canonical: (((r_s55) * (2193)) + ((3) * (c_s55))) + (10)}
	t3173 = image_s0[t3174]
	t3220 = 6
	t3219 = unsharpKernel_s0[t3220]
	t3172 = t3173 * t3219 {canonical: (image_s0[(((r_s55) * (2193)) + ((3) * (c_s55))) + (10)]) * (unsharpKernel_s0[6])}
	unsharpMask_s0[t3151] += t3172
	t3237 = 731
	t3235 = r_s55 * t3237 {canonical: (r_s55) * (731)}
	t3234 = t3235 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3259 = 731
	t3257 = r_s55 * t3259 {canonical: (r_s55) * (731)}
	t3256 = t3257 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3255 = unsharpMask_s0[t3256]
	unsharpMask_s0[t3234] = t3255 / kernel_sum_s0 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) / (kernel_sum_s0)}
next=3287
], Scope = 58
UID 3287 CFEndOfMiniCFG [next=2434]

UID 2434 CFBlock [ miniCFG=3286, next=2424], Scope = 58

MiniCFG: UID 3295 CFBlock [
	t3291 = 1
	c_s55 += t3291
next=3296
], Scope = 56
UID 3296 CFEndOfMiniCFG [next=2424]

UID 2424 CFBlock [ miniCFG=3295, next=2435], Scope = 56

MiniCFG: UID 3308 CFBlock [
	c_s55 = cols_s0 - center_s55 {canonical: (cols_s0) - (center_s55)}
next=3309
], Scope = 56
UID 3309 CFEndOfMiniCFG [next=2437]

UID 2437 CFBlock [ miniCFG=3308, next=2441], Scope = 56

MiniCFG: UID 3319 CFBlock [
	t3310 = c_s55 < cols_s0 {canonical: (c_s55) < (cols_s0)}
next=3321
], Scope = 56
UID 3321 CFEndOfMiniCFG [next=2441]

UID 2441 CFConditional [ miniCFG=3319, boolTemp=t3310, ifTrue=2440, ifFalse=2414], Scope = 56

MiniCFG: UID 3389 CFBlock [
	t3327 = 731
	t3325 = r_s55 * t3327 {canonical: (r_s55) * (731)}
	t3324 = t3325 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3349 = 2193
	t3347 = r_s55 * t3349 {canonical: (r_s55) * (2193)}
	t3361 = 3
	t3359 = c_s55 * t3361 {canonical: (c_s55) * (3)}
	t3346 = t3347 + t3359 {canonical: ((r_s55) * (2193)) + ((c_s55) * (3))}
	t3378 = 1
	t3345 = t3346 + t3378 {canonical: (((r_s55) * (2193)) + ((c_s55) * (3))) + (1)}
	unsharpMask_s0[t3324] = image_s0[t3345]
next=3390
], Scope = 59
UID 3390 CFEndOfMiniCFG [next=2440]

UID 2440 CFBlock [ miniCFG=3389, next=2438], Scope = 59

MiniCFG: UID 3398 CFBlock [
	t3394 = 1
	c_s55 += t3394
next=3399
], Scope = 56
UID 3399 CFEndOfMiniCFG [next=2438]

UID 2438 CFBlock [ miniCFG=3398, next=2441], Scope = 56

MiniCFG: UID 3407 CFBlock [
	t3403 = 1
	r_s55 += t3403
next=3408
], Scope = 55
UID 3408 CFEndOfMiniCFG [next=2414]

UID 2414 CFBlock [ miniCFG=3407, next=2442], Scope = 55

MiniCFG: UID 3432 CFBlock [
	t3411 = center_s55 {canonical: center_s55}
	t3416 = center_s55 {canonical: center_s55}
	t3415 = unsharpKernel_s0[t3416]
	unsharpKernel_s0[t3411] = t3415 - kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s55]) - (kernel_sum_s0)}
	c_s55 = 0
next=3433
], Scope = 55
UID 3433 CFEndOfMiniCFG [next=2445]

UID 2445 CFBlock [ miniCFG=3432, next=2480], Scope = 55

MiniCFG: UID 3443 CFBlock [
	t3434 = c_s55 < cols_s0 {canonical: (c_s55) < (cols_s0)}
next=3445
], Scope = 55
UID 3445 CFEndOfMiniCFG [next=2480]

UID 2480 CFConditional [ miniCFG=3443, boolTemp=t3434, ifTrue=2452, ifFalse=2481], Scope = 55

MiniCFG: UID 3487 CFBlock [
	t3449 = c_s55 {canonical: c_s55}
	m1_s60 = unsharpMask_s0[t3449]
	t3458 = 731
	t3456 = c_s55 + t3458 {canonical: (c_s55) + (731)}
	m2_s60 = unsharpMask_s0[t3456]
	t3473 = 1462
	t3471 = c_s55 + t3473 {canonical: (c_s55) + (1462)}
	m3_s60 = unsharpMask_s0[t3471]
	r_s55 = 0
next=3488
], Scope = 60
UID 3488 CFEndOfMiniCFG [next=2452]

UID 2452 CFBlock [ miniCFG=3487, next=2456], Scope = 60

MiniCFG: UID 3498 CFBlock [
	t3489 = r_s55 < center_s55 {canonical: (r_s55) < (center_s55)}
next=3500
], Scope = 60
UID 3500 CFEndOfMiniCFG [next=2456]

UID 2456 CFConditional [ miniCFG=3498, boolTemp=t3489, ifTrue=2455, ifFalse=2458], Scope = 60

MiniCFG: UID 3525 CFBlock [
	t3506 = 731
	t3504 = r_s55 * t3506 {canonical: (r_s55) * (731)}
	t3503 = t3504 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	unsharpMask_s0[t3503] = 0
next=3526
], Scope = 61
UID 3526 CFEndOfMiniCFG [next=2455]

UID 2455 CFBlock [ miniCFG=3525, next=2453], Scope = 61

MiniCFG: UID 3534 CFBlock [
	t3530 = 1
	r_s55 += t3530
next=3535
], Scope = 60
UID 3535 CFEndOfMiniCFG [next=2453]

UID 2453 CFBlock [ miniCFG=3534, next=2456], Scope = 60

MiniCFG: UID 3541 CFBlock [
	r_s55 = center_s55 {canonical: center_s55}
next=3542
], Scope = 60
UID 3542 CFEndOfMiniCFG [next=2458]

UID 2458 CFBlock [ miniCFG=3541, next=2473], Scope = 60

MiniCFG: UID 3561 CFBlock [
	t3545 = rows_s0 - center_s55 {canonical: (rows_s0) - (center_s55)}
	t3543 = r_s55 < t3545 {canonical: (r_s55) < ((rows_s0) - (center_s55))}
next=3563
], Scope = 60
UID 3563 CFEndOfMiniCFG [next=2473]

UID 2473 CFConditional [ miniCFG=3561, boolTemp=t3543, ifTrue=2472, ifFalse=2475], Scope = 60

MiniCFG: UID 4105 CFBlock [
	dot_s62 = 0
	t3575 = 731
	t3573 = r_s55 * t3575 {canonical: (r_s55) * (731)}
	t3572 = t3573 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3571 = unsharpMask_s0[t3572]
	t3598 = 0
	t3597 = unsharpKernel_s0[t3598]
	t3595 = m1_s60 * t3597 {canonical: (m1_s60) * (unsharpKernel_s0[0])}
	t3570 = t3571 + t3595 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((m1_s60) * (unsharpKernel_s0[0]))}
	dot_s62 += t3570
	t3625 = 731
	t3623 = r_s55 * t3625 {canonical: (r_s55) * (731)}
	t3622 = t3623 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3621 = unsharpMask_s0[t3622]
	t3648 = 1
	t3647 = unsharpKernel_s0[t3648]
	t3645 = m2_s60 * t3647 {canonical: (m2_s60) * (unsharpKernel_s0[1])}
	t3620 = t3621 + t3645 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((m2_s60) * (unsharpKernel_s0[1]))}
	dot_s62 += t3620
	t3675 = 731
	t3673 = r_s55 * t3675 {canonical: (r_s55) * (731)}
	t3672 = t3673 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3671 = unsharpMask_s0[t3672]
	t3698 = 2
	t3697 = unsharpKernel_s0[t3698]
	t3695 = m3_s60 * t3697 {canonical: (m3_s60) * (unsharpKernel_s0[2])}
	t3670 = t3671 + t3695 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((m3_s60) * (unsharpKernel_s0[2]))}
	dot_s62 += t3670
	t3725 = 731
	t3723 = r_s55 * t3725 {canonical: (r_s55) * (731)}
	t3722 = t3723 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3721 = unsharpMask_s0[t3722]
	t3749 = 731
	t3748 = t3749 * r_s55 {canonical: (731) * (r_s55)}
	t3747 = t3748 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	t3746 = unsharpMask_s0[t3747]
	t3771 = 3
	t3770 = unsharpKernel_s0[t3771]
	t3745 = t3746 * t3770 {canonical: (unsharpMask_s0[((731) * (r_s55)) + (c_s55)]) * (unsharpKernel_s0[3])}
	t3720 = t3721 + t3745 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[((731) * (r_s55)) + (c_s55)]) * (unsharpKernel_s0[3]))}
	dot_s62 += t3720
	t3798 = 731
	t3796 = r_s55 * t3798 {canonical: (r_s55) * (731)}
	t3795 = t3796 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3794 = unsharpMask_s0[t3795]
	t3823 = 731
	t3822 = t3823 * r_s55 {canonical: (731) * (r_s55)}
	t3821 = t3822 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	t3842 = 731
	t3820 = t3821 + t3842 {canonical: (((731) * (r_s55)) + (c_s55)) + (731)}
	t3819 = unsharpMask_s0[t3820]
	t3855 = 4
	t3854 = unsharpKernel_s0[t3855]
	t3818 = t3819 * t3854 {canonical: (unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (731)]) * (unsharpKernel_s0[4])}
	t3793 = t3794 + t3818 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (731)]) * (unsharpKernel_s0[4]))}
	dot_s62 += t3793
	t3882 = 731
	t3880 = r_s55 * t3882 {canonical: (r_s55) * (731)}
	t3879 = t3880 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3878 = unsharpMask_s0[t3879]
	t3907 = 731
	t3906 = t3907 * r_s55 {canonical: (731) * (r_s55)}
	t3905 = t3906 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	t3926 = 1462
	t3904 = t3905 + t3926 {canonical: (((731) * (r_s55)) + (c_s55)) + (1462)}
	t3903 = unsharpMask_s0[t3904]
	t3939 = 5
	t3938 = unsharpKernel_s0[t3939]
	t3902 = t3903 * t3938 {canonical: (unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (1462)]) * (unsharpKernel_s0[5])}
	t3877 = t3878 + t3902 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (1462)]) * (unsharpKernel_s0[5]))}
	dot_s62 += t3877
	t3966 = 731
	t3964 = r_s55 * t3966 {canonical: (r_s55) * (731)}
	t3963 = t3964 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	t3962 = unsharpMask_s0[t3963]
	t3991 = 731
	t3990 = t3991 * r_s55 {canonical: (731) * (r_s55)}
	t3989 = t3990 + c_s55 {canonical: ((731) * (r_s55)) + (c_s55)}
	t4010 = 2193
	t3988 = t3989 + t4010 {canonical: (((731) * (r_s55)) + (c_s55)) + (2193)}
	t3987 = unsharpMask_s0[t3988]
	t4023 = 6
	t4022 = unsharpKernel_s0[t4023]
	t3986 = t3987 * t4022 {canonical: (unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (2193)]) * (unsharpKernel_s0[6])}
	t3961 = t3962 + t3986 {canonical: (unsharpMask_s0[((r_s55) * (731)) + (c_s55)]) + ((unsharpMask_s0[(((731) * (r_s55)) + (c_s55)) + (2193)]) * (unsharpKernel_s0[6]))}
	dot_s62 += t3961
	m1_s60 = m2_s60 {canonical: m2_s60}
	m2_s60 = m3_s60 {canonical: m3_s60}
	t4056 = 731
	t4054 = r_s55 * t4056 {canonical: (r_s55) * (731)}
	t4053 = t4054 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	m3_s60 = unsharpMask_s0[t4053]
	t4079 = 731
	t4077 = r_s55 * t4079 {canonical: (r_s55) * (731)}
	t4076 = t4077 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	unsharpMask_s0[t4076] = dot_s62 / kernel_sum_s0 {canonical: (dot_s62) / (kernel_sum_s0)}
next=4106
], Scope = 62
UID 4106 CFEndOfMiniCFG [next=2472]

UID 2472 CFBlock [ miniCFG=4105, next=2459], Scope = 62

MiniCFG: UID 4114 CFBlock [
	t4110 = 1
	r_s55 += t4110
next=4115
], Scope = 60
UID 4115 CFEndOfMiniCFG [next=2459]

UID 2459 CFBlock [ miniCFG=4114, next=2473], Scope = 60

MiniCFG: UID 4127 CFBlock [
	r_s55 = rows_s0 - center_s55 {canonical: (rows_s0) - (center_s55)}
next=4128
], Scope = 60
UID 4128 CFEndOfMiniCFG [next=2475]

UID 2475 CFBlock [ miniCFG=4127, next=2479], Scope = 60

MiniCFG: UID 4138 CFBlock [
	t4129 = r_s55 < rows_s0 {canonical: (r_s55) < (rows_s0)}
next=4140
], Scope = 60
UID 4140 CFEndOfMiniCFG [next=2479]

UID 2479 CFConditional [ miniCFG=4138, boolTemp=t4129, ifTrue=2478, ifFalse=2446], Scope = 60

MiniCFG: UID 4165 CFBlock [
	t4146 = 731
	t4144 = r_s55 * t4146 {canonical: (r_s55) * (731)}
	t4143 = t4144 + c_s55 {canonical: ((r_s55) * (731)) + (c_s55)}
	unsharpMask_s0[t4143] = 0
next=4166
], Scope = 63
UID 4166 CFEndOfMiniCFG [next=2478]

UID 2478 CFBlock [ miniCFG=4165, next=2476], Scope = 63

MiniCFG: UID 4174 CFBlock [
	t4170 = 1
	r_s55 += t4170
next=4175
], Scope = 60
UID 4175 CFEndOfMiniCFG [next=2476]

UID 2476 CFBlock [ miniCFG=4174, next=2479], Scope = 60

MiniCFG: UID 4183 CFBlock [
	t4179 = 1
	c_s55 += t4179
next=4184
], Scope = 55
UID 4184 CFEndOfMiniCFG [next=2446]

UID 2446 CFBlock [ miniCFG=4183, next=2480], Scope = 55

MiniCFG: UID 4205 CFBlock [
	t4187 = center_s55 {canonical: center_s55}
	t4192 = center_s55 {canonical: center_s55}
	t4191 = unsharpKernel_s0[t4192]
	unsharpKernel_s0[t4187] = t4191 + kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s55]) + (kernel_sum_s0)}
next=4206
], Scope = 55
UID 4206 CFEndOfMiniCFG [next=2481]

UID 2481 CFBlock [ miniCFG=4205, next=2482], Scope = 55
UID 2482 CFReturn
----------
CFG for sharpenS
----------

MiniCFG: UID 4230 CFBlock [
	c_s80 = 0
next=4231
], Scope = 80
UID 4231 CFEndOfMiniCFG [next=4211]

UID 4211 CFBlock [ miniCFG=4230, next=4224], Scope = 80

MiniCFG: UID 4241 CFBlock [
	t4232 = c_s80 < cols_s0 {canonical: (c_s80) < (cols_s0)}
next=4243
], Scope = 80
UID 4243 CFEndOfMiniCFG [next=4224]

UID 4224 CFConditional [ miniCFG=4241, boolTemp=t4232, ifTrue=4215, ifFalse=4225], Scope = 80

MiniCFG: UID 4248 CFBlock [
	r_s80 = 0
next=4249
], Scope = 81
UID 4249 CFEndOfMiniCFG [next=4215]

UID 4215 CFBlock [ miniCFG=4248, next=4223], Scope = 81

MiniCFG: UID 4259 CFBlock [
	t4250 = r_s80 < rows_s0 {canonical: (r_s80) < (rows_s0)}
next=4261
], Scope = 81
UID 4261 CFEndOfMiniCFG [next=4223]

UID 4223 CFConditional [ miniCFG=4259, boolTemp=t4250, ifTrue=4218, ifFalse=4212], Scope = 81

MiniCFG: UID 4410 CFBlock [
	t4268 = 2193
	t4266 = r_s80 * t4268 {canonical: (r_s80) * (2193)}
	t4280 = 3
	t4278 = c_s80 * t4280 {canonical: (c_s80) * (3)}
	t4265 = t4266 + t4278 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	t4297 = 1
	t4264 = t4265 + t4297 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	t4313 = 2193
	t4311 = r_s80 * t4313 {canonical: (r_s80) * (2193)}
	t4325 = 3
	t4323 = c_s80 * t4325 {canonical: (c_s80) * (3)}
	t4310 = t4311 + t4323 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	t4342 = 1
	t4309 = t4310 + t4342 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	t4308 = image_s0[t4309]
	t4362 = 731
	t4360 = r_s80 * t4362 {canonical: (r_s80) * (731)}
	t4359 = t4360 + c_s80 {canonical: ((r_s80) * (731)) + (c_s80)}
	t4358 = unsharpMask_s0[t4359]
	t4356 = amount_s79 * t4358 {canonical: (amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)])}
	t4354 = channelOne_s79 + t4356 {canonical: (channelOne_s79) + ((amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)]))}
	t4307 = t4308 * t4354 {canonical: (image_s0[(((r_s80) * (2193)) + ((c_s80) * (3))) + (1)]) * ((channelOne_s79) + ((amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)])))}
	image_s0[t4264] = t4307 / channelOne_s79 {canonical: ((image_s0[(((r_s80) * (2193)) + ((c_s80) * (3))) + (1)]) * ((channelOne_s79) + ((amount_s79) * (unsharpMask_s0[((r_s80) * (731)) + (c_s80)])))) / (channelOne_s79)}
next=4411
], Scope = 82
UID 4411 CFEndOfMiniCFG [next=4218]

UID 4218 CFBlock [ miniCFG=4410, next=4222], Scope = 82

MiniCFG: UID 4466 CFBlock [
	t4418 = 2193
	t4416 = r_s80 * t4418 {canonical: (r_s80) * (2193)}
	t4430 = 3
	t4428 = c_s80 * t4430 {canonical: (c_s80) * (3)}
	t4415 = t4416 + t4428 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	t4447 = 1
	t4414 = t4415 + t4447 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	t4413 = image_s0[t4414]
	t4412 = t4413 >= channelOne_s79 {canonical: (image_s0[(((r_s80) * (2193)) + ((c_s80) * (3))) + (1)]) >= (channelOne_s79)}
next=4468
], Scope = 82
UID 4468 CFEndOfMiniCFG [next=4222]

UID 4222 CFConditional [ miniCFG=4466, boolTemp=t4412, ifTrue=4221, ifFalse=4216], Scope = 82

MiniCFG: UID 4524 CFBlock [
	t4475 = 2193
	t4473 = r_s80 * t4475 {canonical: (r_s80) * (2193)}
	t4487 = 3
	t4485 = c_s80 * t4487 {canonical: (c_s80) * (3)}
	t4472 = t4473 + t4485 {canonical: ((r_s80) * (2193)) + ((c_s80) * (3))}
	t4504 = 1
	t4471 = t4472 + t4504 {canonical: (((r_s80) * (2193)) + ((c_s80) * (3))) + (1)}
	t4515 = 1
	image_s0[t4471] = channelOne_s79 - t4515 {canonical: (channelOne_s79) - (1)}
next=4525
], Scope = 83
UID 4525 CFEndOfMiniCFG [next=4221]

UID 4221 CFBlock [ miniCFG=4524, next=4216], Scope = 83

MiniCFG: UID 4533 CFBlock [
	t4529 = 1
	r_s80 += t4529
next=4534
], Scope = 81
UID 4534 CFEndOfMiniCFG [next=4216]

UID 4216 CFBlock [ miniCFG=4533, next=4223], Scope = 81

MiniCFG: UID 4542 CFBlock [
	t4538 = 1
	c_s80 += t4538
next=4543
], Scope = 80
UID 4543 CFEndOfMiniCFG [next=4212]

UID 4212 CFBlock [ miniCFG=4542, next=4224], Scope = 80
UID 4225 CFReturn
----------
CFG for write_file
----------

MiniCFG: UID 4576 CFBlock [
	t4564 = cols_s0 {canonical: cols_s0}
	t4568 = rows_s0 {canonical: rows_s0}
	ppm_open_for_write_s0["output/output.ppm", t4564, t4568]
	r_s6 = 0
next=4577
], Scope = 6
UID 4577 CFEndOfMiniCFG [next=4549]

UID 4549 CFBlock [ miniCFG=4576, next=4558], Scope = 6

MiniCFG: UID 4587 CFBlock [
	t4578 = r_s6 < rows_s0 {canonical: (r_s6) < (rows_s0)}
next=4589
], Scope = 6
UID 4589 CFEndOfMiniCFG [next=4558]

UID 4558 CFConditional [ miniCFG=4587, boolTemp=t4578, ifTrue=4553, ifFalse=4559], Scope = 6

MiniCFG: UID 4594 CFBlock [
	c_s6 = 0
next=4595
], Scope = 7
UID 4595 CFEndOfMiniCFG [next=4553]

UID 4553 CFBlock [ miniCFG=4594, next=4557], Scope = 7

MiniCFG: UID 4605 CFBlock [
	t4596 = c_s6 < cols_s0 {canonical: (c_s6) < (cols_s0)}
next=4607
], Scope = 7
UID 4607 CFEndOfMiniCFG [next=4557]

UID 4557 CFConditional [ miniCFG=4605, boolTemp=t4596, ifTrue=4556, ifFalse=4550], Scope = 7

MiniCFG: UID 4739 CFBlock [
	t4615 = 2193
	t4613 = r_s6 * t4615 {canonical: (r_s6) * (2193)}
	t4627 = 3
	t4625 = c_s6 * t4627 {canonical: (c_s6) * (3)}
	t4612 = t4613 + t4625 {canonical: ((r_s6) * (2193)) + ((c_s6) * (3))}
	t4611 = image_s0[t4612]
	t4651 = 2193
	t4649 = r_s6 * t4651 {canonical: (r_s6) * (2193)}
	t4663 = 3
	t4661 = c_s6 * t4663 {canonical: (c_s6) * (3)}
	t4648 = t4649 + t4661 {canonical: ((r_s6) * (2193)) + ((c_s6) * (3))}
	t4680 = 1
	t4647 = t4648 + t4680 {canonical: (((r_s6) * (2193)) + ((c_s6) * (3))) + (1)}
	t4646 = image_s0[t4647]
	t4697 = 2193
	t4695 = r_s6 * t4697 {canonical: (r_s6) * (2193)}
	t4709 = 3
	t4707 = c_s6 * t4709 {canonical: (c_s6) * (3)}
	t4694 = t4695 + t4707 {canonical: ((r_s6) * (2193)) + ((c_s6) * (3))}
	t4726 = 2
	t4693 = t4694 + t4726 {canonical: (((r_s6) * (2193)) + ((c_s6) * (3))) + (2)}
	t4692 = image_s0[t4693]
	ppm_write_next_pixel_s0[t4611, t4646, t4692]
next=4740
], Scope = 8
UID 4740 CFEndOfMiniCFG [next=4556]

UID 4556 CFBlock [ miniCFG=4739, next=4554], Scope = 8

MiniCFG: UID 4748 CFBlock [
	t4744 = 1
	c_s6 += t4744
next=4749
], Scope = 7
UID 4749 CFEndOfMiniCFG [next=4554]

UID 4554 CFBlock [ miniCFG=4748, next=4557], Scope = 7

MiniCFG: UID 4757 CFBlock [
	t4753 = 1
	r_s6 += t4753
next=4758
], Scope = 6
UID 4758 CFEndOfMiniCFG [next=4550]

UID 4550 CFBlock [ miniCFG=4757, next=4558], Scope = 6

MiniCFG: UID 4763 CFBlock [
	ppm_close_s0[]
next=4764
], Scope = 6
UID 4764 CFEndOfMiniCFG [next=4559]

UID 4559 CFBlock [ miniCFG=4763, next=4560], Scope = 6
UID 4560 CFReturn
----------
CFG for sharpenV
----------

MiniCFG: UID 4788 CFBlock [
	c_s85 = 0
next=4789
], Scope = 85
UID 4789 CFEndOfMiniCFG [next=4769]

UID 4769 CFBlock [ miniCFG=4788, next=4782], Scope = 85

MiniCFG: UID 4799 CFBlock [
	t4790 = c_s85 < cols_s0 {canonical: (c_s85) < (cols_s0)}
next=4801
], Scope = 85
UID 4801 CFEndOfMiniCFG [next=4782]

UID 4782 CFConditional [ miniCFG=4799, boolTemp=t4790, ifTrue=4773, ifFalse=4783], Scope = 85

MiniCFG: UID 4806 CFBlock [
	r_s85 = 0
next=4807
], Scope = 86
UID 4807 CFEndOfMiniCFG [next=4773]

UID 4773 CFBlock [ miniCFG=4806, next=4781], Scope = 86

MiniCFG: UID 4817 CFBlock [
	t4808 = r_s85 < rows_s0 {canonical: (r_s85) < (rows_s0)}
next=4819
], Scope = 86
UID 4819 CFEndOfMiniCFG [next=4781]

UID 4781 CFConditional [ miniCFG=4817, boolTemp=t4808, ifTrue=4776, ifFalse=4770], Scope = 86

MiniCFG: UID 4968 CFBlock [
	t4826 = 2193
	t4824 = r_s85 * t4826 {canonical: (r_s85) * (2193)}
	t4838 = 3
	t4836 = c_s85 * t4838 {canonical: (c_s85) * (3)}
	t4823 = t4824 + t4836 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	t4855 = 2
	t4822 = t4823 + t4855 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	t4871 = 2193
	t4869 = r_s85 * t4871 {canonical: (r_s85) * (2193)}
	t4883 = 3
	t4881 = c_s85 * t4883 {canonical: (c_s85) * (3)}
	t4868 = t4869 + t4881 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	t4900 = 2
	t4867 = t4868 + t4900 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	t4866 = image_s0[t4867]
	t4920 = 731
	t4918 = r_s85 * t4920 {canonical: (r_s85) * (731)}
	t4917 = t4918 + c_s85 {canonical: ((r_s85) * (731)) + (c_s85)}
	t4916 = unsharpMask_s0[t4917]
	t4914 = amount_s84 * t4916 {canonical: (amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)])}
	t4912 = channelOne_s84 + t4914 {canonical: (channelOne_s84) + ((amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)]))}
	t4865 = t4866 * t4912 {canonical: (image_s0[(((r_s85) * (2193)) + ((c_s85) * (3))) + (2)]) * ((channelOne_s84) + ((amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)])))}
	image_s0[t4822] = t4865 / channelOne_s84 {canonical: ((image_s0[(((r_s85) * (2193)) + ((c_s85) * (3))) + (2)]) * ((channelOne_s84) + ((amount_s84) * (unsharpMask_s0[((r_s85) * (731)) + (c_s85)])))) / (channelOne_s84)}
next=4969
], Scope = 87
UID 4969 CFEndOfMiniCFG [next=4776]

UID 4776 CFBlock [ miniCFG=4968, next=4780], Scope = 87

MiniCFG: UID 5024 CFBlock [
	t4976 = 2193
	t4974 = r_s85 * t4976 {canonical: (r_s85) * (2193)}
	t4988 = 3
	t4986 = c_s85 * t4988 {canonical: (c_s85) * (3)}
	t4973 = t4974 + t4986 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	t5005 = 2
	t4972 = t4973 + t5005 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	t4971 = image_s0[t4972]
	t4970 = t4971 >= channelOne_s84 {canonical: (image_s0[(((r_s85) * (2193)) + ((c_s85) * (3))) + (2)]) >= (channelOne_s84)}
next=5026
], Scope = 87
UID 5026 CFEndOfMiniCFG [next=4780]

UID 4780 CFConditional [ miniCFG=5024, boolTemp=t4970, ifTrue=4779, ifFalse=4774], Scope = 87

MiniCFG: UID 5082 CFBlock [
	t5033 = 2193
	t5031 = r_s85 * t5033 {canonical: (r_s85) * (2193)}
	t5045 = 3
	t5043 = c_s85 * t5045 {canonical: (c_s85) * (3)}
	t5030 = t5031 + t5043 {canonical: ((r_s85) * (2193)) + ((c_s85) * (3))}
	t5062 = 2
	t5029 = t5030 + t5062 {canonical: (((r_s85) * (2193)) + ((c_s85) * (3))) + (2)}
	t5073 = 1
	image_s0[t5029] = channelOne_s84 - t5073 {canonical: (channelOne_s84) - (1)}
next=5083
], Scope = 88
UID 5083 CFEndOfMiniCFG [next=4779]

UID 4779 CFBlock [ miniCFG=5082, next=4774], Scope = 88

MiniCFG: UID 5091 CFBlock [
	t5087 = 1
	r_s85 += t5087
next=5092
], Scope = 86
UID 5092 CFEndOfMiniCFG [next=4774]

UID 4774 CFBlock [ miniCFG=5091, next=4781], Scope = 86

MiniCFG: UID 5100 CFBlock [
	t5096 = 1
	c_s85 += t5096
next=5101
], Scope = 85
UID 5101 CFEndOfMiniCFG [next=4770]

UID 4770 CFBlock [ miniCFG=5100, next=4782], Scope = 85
UID 4783 CFReturn
----------
CFG for convert2RGB
----------

MiniCFG: UID 5187 CFBlock [
	row_s29 = 0
next=5188
], Scope = 29
UID 5188 CFEndOfMiniCFG [next=5106]

UID 5106 CFBlock [ miniCFG=5187, next=5181], Scope = 29

MiniCFG: UID 5198 CFBlock [
	t5189 = row_s29 < rows_s0 {canonical: (row_s29) < (rows_s0)}
next=5200
], Scope = 29
UID 5200 CFEndOfMiniCFG [next=5181]

UID 5181 CFConditional [ miniCFG=5198, boolTemp=t5189, ifTrue=5110, ifFalse=5182], Scope = 29

MiniCFG: UID 5205 CFBlock [
	col_s29 = 0
next=5206
], Scope = 30
UID 5206 CFEndOfMiniCFG [next=5110]

UID 5110 CFBlock [ miniCFG=5205, next=5180], Scope = 30

MiniCFG: UID 5216 CFBlock [
	t5207 = col_s29 < cols_s0 {canonical: (col_s29) < (cols_s0)}
next=5218
], Scope = 30
UID 5218 CFEndOfMiniCFG [next=5180]

UID 5180 CFConditional [ miniCFG=5216, boolTemp=t5207, ifTrue=5121, ifFalse=5107], Scope = 30

MiniCFG: UID 5247 CFBlock [
	r_s31 = 0
	g_s31 = 0
	b_s31 = 0
	v_s31 = 0
	j_s31 = 0
	f_s31 = 0
	p_s31 = 0
	q_s31 = 0
	t_s31 = 0
next=5248
], Scope = 31
UID 5248 CFEndOfMiniCFG [next=5121]

UID 5121 CFBlock [ miniCFG=5247, next=5176], Scope = 31

MiniCFG: UID 5305 CFBlock [
	t5255 = 2193
	t5253 = row_s29 * t5255 {canonical: (row_s29) * (2193)}
	t5267 = 3
	t5265 = col_s29 * t5267 {canonical: (col_s29) * (3)}
	t5252 = t5253 + t5265 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5284 = 1
	t5251 = t5252 + t5284 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	t5250 = image_s0[t5251]
	t5296 = 0
	t5249 = t5250 == t5296 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) == (0)}
next=5307
], Scope = 31
UID 5307 CFEndOfMiniCFG [next=5176]

UID 5176 CFConditional [ miniCFG=5305, boolTemp=t5249, ifTrue=5126, ifFalse=5133], Scope = 31

MiniCFG: UID 5480 CFBlock [
	t5316 = 2193
	t5314 = row_s29 * t5316 {canonical: (row_s29) * (2193)}
	t5328 = 3
	t5326 = col_s29 * t5328 {canonical: (col_s29) * (3)}
	t5313 = t5314 + t5326 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5345 = 2
	t5312 = t5313 + t5345 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	t5311 = image_s0[t5312]
	t5357 = 4
	r_s31 = t5311 / t5357 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
	t5373 = 2193
	t5371 = row_s29 * t5373 {canonical: (row_s29) * (2193)}
	t5385 = 3
	t5383 = col_s29 * t5385 {canonical: (col_s29) * (3)}
	t5370 = t5371 + t5383 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5402 = 2
	t5369 = t5370 + t5402 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	t5368 = image_s0[t5369]
	t5414 = 4
	g_s31 = t5368 / t5414 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
	t5430 = 2193
	t5428 = row_s29 * t5430 {canonical: (row_s29) * (2193)}
	t5442 = 3
	t5440 = col_s29 * t5442 {canonical: (col_s29) * (3)}
	t5427 = t5428 + t5440 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5459 = 2
	t5426 = t5427 + t5459 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	t5425 = image_s0[t5426]
	t5471 = 4
	b_s31 = t5425 / t5471 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
next=5481
], Scope = 32
UID 5481 CFEndOfMiniCFG [next=5126]

UID 5126 CFBlock [ miniCFG=5480, next=5179], Scope = 32

MiniCFG: UID 5621 CFBlock [
	t5488 = 2193
	t5486 = row_s29 * t5488 {canonical: (row_s29) * (2193)}
	t5500 = 3
	t5498 = col_s29 * t5500 {canonical: (col_s29) * (3)}
	t5485 = t5486 + t5498 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5517 = 0
	t5484 = t5485 + t5517 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (0)}
	image_s0[t5484] = r_s31 {canonical: r_s31}
	t5534 = 2193
	t5532 = row_s29 * t5534 {canonical: (row_s29) * (2193)}
	t5546 = 3
	t5544 = col_s29 * t5546 {canonical: (col_s29) * (3)}
	t5531 = t5532 + t5544 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5563 = 1
	t5530 = t5531 + t5563 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	image_s0[t5530] = g_s31 {canonical: g_s31}
	t5580 = 2193
	t5578 = row_s29 * t5580 {canonical: (row_s29) * (2193)}
	t5592 = 3
	t5590 = col_s29 * t5592 {canonical: (col_s29) * (3)}
	t5577 = t5578 + t5590 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5609 = 2
	t5576 = t5577 + t5609 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	image_s0[t5576] = b_s31 {canonical: b_s31}
next=5622
], Scope = 31
UID 5622 CFEndOfMiniCFG [next=5179]

UID 5179 CFBlock [ miniCFG=5621, next=5111], Scope = 31

MiniCFG: UID 5630 CFBlock [
	t5626 = 1
	col_s29 += t5626
next=5631
], Scope = 30
UID 5631 CFEndOfMiniCFG [next=5111]

UID 5111 CFBlock [ miniCFG=5630, next=5180], Scope = 30

MiniCFG: UID 6287 CFBlock [
	t5641 = 2193
	t5639 = row_s29 * t5641 {canonical: (row_s29) * (2193)}
	t5653 = 3
	t5651 = col_s29 * t5653 {canonical: (col_s29) * (3)}
	t5638 = t5639 + t5651 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5670 = 0
	t5637 = t5638 + t5670 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (0)}
	t5636 = image_s0[t5637]
	t5682 = 60
	t5635 = t5636 / t5682 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (0)]) / (60)}
	t5692 = 6
	j_s31 = t5635 % t5692 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (0)]) / (60)) % (6)}
	t5708 = 2193
	t5706 = row_s29 * t5708 {canonical: (row_s29) * (2193)}
	t5720 = 3
	t5718 = col_s29 * t5720 {canonical: (col_s29) * (3)}
	t5705 = t5706 + t5718 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5737 = 0
	t5704 = t5705 + t5737 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (0)}
	t5703 = image_s0[t5704]
	t5749 = 60
	f_s31 = t5703 % t5749 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (0)]) % (60)}
	t5766 = 2193
	t5764 = row_s29 * t5766 {canonical: (row_s29) * (2193)}
	t5778 = 3
	t5776 = col_s29 * t5778 {canonical: (col_s29) * (3)}
	t5763 = t5764 + t5776 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5795 = 2
	t5762 = t5763 + t5795 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	t5761 = image_s0[t5762]
	t5808 = 1024
	t5816 = 2193
	t5814 = row_s29 * t5816 {canonical: (row_s29) * (2193)}
	t5828 = 3
	t5826 = col_s29 * t5828 {canonical: (col_s29) * (3)}
	t5813 = t5814 + t5826 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5845 = 1
	t5812 = t5813 + t5845 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	t5811 = image_s0[t5812]
	t5807 = t5808 - t5811 {canonical: (1024) - (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)])}
	t5760 = t5761 * t5807 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * ((1024) - (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]))}
	t5872 = 1024
	t5875 = 4
	t5871 = t5872 * t5875 {canonical: (1024) * (4)}
	p_s31 = t5760 / t5871 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * ((1024) - (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]))) / ((1024) * (4))}
	t5899 = 2193
	t5897 = row_s29 * t5899 {canonical: (row_s29) * (2193)}
	t5911 = 3
	t5909 = col_s29 * t5911 {canonical: (col_s29) * (3)}
	t5896 = t5897 + t5909 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5928 = 2
	t5895 = t5896 + t5928 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	t5894 = image_s0[t5895]
	t5942 = 1024
	t5945 = 60
	t5941 = t5942 * t5945 {canonical: (1024) * (60)}
	t5961 = 2193
	t5959 = row_s29 * t5961 {canonical: (row_s29) * (2193)}
	t5973 = 3
	t5971 = col_s29 * t5973 {canonical: (col_s29) * (3)}
	t5958 = t5959 + t5971 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t5990 = 1
	t5957 = t5958 + t5990 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	t5956 = image_s0[t5957]
	t5955 = t5956 * f_s31 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31)}
	t5940 = t5941 - t5955 {canonical: ((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31))}
	t5893 = t5894 * t5940 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31)))}
	t6026 = 1024
	t6029 = 60
	t6025 = t6026 * t6029 {canonical: (1024) * (60)}
	t6039 = 4
	t6024 = t6025 * t6039 {canonical: ((1024) * (60)) * (4)}
	q_s31 = t5893 / t6024 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * (f_s31)))) / (((1024) * (60)) * (4))}
	t6063 = 2193
	t6061 = row_s29 * t6063 {canonical: (row_s29) * (2193)}
	t6075 = 3
	t6073 = col_s29 * t6075 {canonical: (col_s29) * (3)}
	t6060 = t6061 + t6073 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t6092 = 2
	t6059 = t6060 + t6092 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	t6058 = image_s0[t6059]
	t6106 = 1024
	t6109 = 60
	t6105 = t6106 * t6109 {canonical: (1024) * (60)}
	t6125 = 2193
	t6123 = row_s29 * t6125 {canonical: (row_s29) * (2193)}
	t6137 = 3
	t6135 = col_s29 * t6137 {canonical: (col_s29) * (3)}
	t6122 = t6123 + t6135 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t6154 = 1
	t6121 = t6122 + t6154 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (1)}
	t6120 = image_s0[t6121]
	t6167 = 60
	t6166 = t6167 - f_s31 {canonical: (60) - (f_s31)}
	t6119 = t6120 * t6166 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31))}
	t6104 = t6105 - t6119 {canonical: ((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31)))}
	t6057 = t6058 * t6104 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31))))}
	t6201 = 1024
	t6204 = 60
	t6200 = t6201 * t6204 {canonical: (1024) * (60)}
	t6214 = 4
	t6199 = t6200 * t6214 {canonical: ((1024) * (60)) * (4)}
	t_s31 = t6057 / t6199 {canonical: ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) * (((1024) * (60)) - ((image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (1)]) * ((60) - (f_s31))))) / (((1024) * (60)) * (4))}
	t6237 = 2193
	t6235 = row_s29 * t6237 {canonical: (row_s29) * (2193)}
	t6249 = 3
	t6247 = col_s29 * t6249 {canonical: (col_s29) * (3)}
	t6234 = t6235 + t6247 {canonical: ((row_s29) * (2193)) + ((col_s29) * (3))}
	t6266 = 2
	t6233 = t6234 + t6266 {canonical: (((row_s29) * (2193)) + ((col_s29) * (3))) + (2)}
	t6232 = image_s0[t6233]
	t6278 = 4
	v_s31 = t6232 / t6278 {canonical: (image_s0[(((row_s29) * (2193)) + ((col_s29) * (3))) + (2)]) / (4)}
next=6288
], Scope = 33
UID 6288 CFEndOfMiniCFG [next=5133]

UID 5133 CFBlock [ miniCFG=6287, next=5139], Scope = 33

MiniCFG: UID 6300 CFBlock [
	t6291 = 0
	t6289 = j_s31 == t6291 {canonical: (j_s31) == (0)}
next=6302
], Scope = 33
UID 6302 CFEndOfMiniCFG [next=5139]

UID 5139 CFConditional [ miniCFG=6300, boolTemp=t6289, ifTrue=5138, ifFalse=5145], Scope = 33

MiniCFG: UID 6316 CFBlock [
	r_s31 = v_s31 {canonical: v_s31}
	g_s31 = t_s31 {canonical: t_s31}
	b_s31 = p_s31 {canonical: p_s31}
next=6317
], Scope = 34
UID 6317 CFEndOfMiniCFG [next=5138]

UID 5138 CFBlock [ miniCFG=6316, next=5145], Scope = 34

MiniCFG: UID 6329 CFBlock [
	t6320 = 1
	t6318 = j_s31 == t6320 {canonical: (j_s31) == (1)}
next=6331
], Scope = 33
UID 6331 CFEndOfMiniCFG [next=5145]

UID 5145 CFConditional [ miniCFG=6329, boolTemp=t6318, ifTrue=5144, ifFalse=5151], Scope = 33

MiniCFG: UID 6345 CFBlock [
	r_s31 = q_s31 {canonical: q_s31}
	g_s31 = v_s31 {canonical: v_s31}
	b_s31 = p_s31 {canonical: p_s31}
next=6346
], Scope = 35
UID 6346 CFEndOfMiniCFG [next=5144]

UID 5144 CFBlock [ miniCFG=6345, next=5151], Scope = 35

MiniCFG: UID 6358 CFBlock [
	t6349 = 2
	t6347 = j_s31 == t6349 {canonical: (j_s31) == (2)}
next=6360
], Scope = 33
UID 6360 CFEndOfMiniCFG [next=5151]

UID 5151 CFConditional [ miniCFG=6358, boolTemp=t6347, ifTrue=5150, ifFalse=5157], Scope = 33

MiniCFG: UID 6374 CFBlock [
	r_s31 = p_s31 {canonical: p_s31}
	g_s31 = v_s31 {canonical: v_s31}
	b_s31 = t_s31 {canonical: t_s31}
next=6375
], Scope = 36
UID 6375 CFEndOfMiniCFG [next=5150]

UID 5150 CFBlock [ miniCFG=6374, next=5157], Scope = 36

MiniCFG: UID 6387 CFBlock [
	t6378 = 3
	t6376 = j_s31 == t6378 {canonical: (j_s31) == (3)}
next=6389
], Scope = 33
UID 6389 CFEndOfMiniCFG [next=5157]

UID 5157 CFConditional [ miniCFG=6387, boolTemp=t6376, ifTrue=5156, ifFalse=5163], Scope = 33

MiniCFG: UID 6403 CFBlock [
	r_s31 = p_s31 {canonical: p_s31}
	g_s31 = q_s31 {canonical: q_s31}
	b_s31 = v_s31 {canonical: v_s31}
next=6404
], Scope = 37
UID 6404 CFEndOfMiniCFG [next=5156]

UID 5156 CFBlock [ miniCFG=6403, next=5163], Scope = 37

MiniCFG: UID 6416 CFBlock [
	t6407 = 4
	t6405 = j_s31 == t6407 {canonical: (j_s31) == (4)}
next=6418
], Scope = 33
UID 6418 CFEndOfMiniCFG [next=5163]

UID 5163 CFConditional [ miniCFG=6416, boolTemp=t6405, ifTrue=5162, ifFalse=5169], Scope = 33

MiniCFG: UID 6432 CFBlock [
	r_s31 = t_s31 {canonical: t_s31}
	g_s31 = p_s31 {canonical: p_s31}
	b_s31 = v_s31 {canonical: v_s31}
next=6433
], Scope = 38
UID 6433 CFEndOfMiniCFG [next=5162]

UID 5162 CFBlock [ miniCFG=6432, next=5169], Scope = 38

MiniCFG: UID 6445 CFBlock [
	t6436 = 5
	t6434 = j_s31 == t6436 {canonical: (j_s31) == (5)}
next=6447
], Scope = 33
UID 6447 CFEndOfMiniCFG [next=5169]

UID 5169 CFConditional [ miniCFG=6445, boolTemp=t6434, ifTrue=5168, ifFalse=5175], Scope = 33

MiniCFG: UID 6461 CFBlock [
	r_s31 = v_s31 {canonical: v_s31}
	g_s31 = p_s31 {canonical: p_s31}
	b_s31 = q_s31 {canonical: q_s31}
next=6462
], Scope = 39
UID 6462 CFEndOfMiniCFG [next=5168]

UID 5168 CFBlock [ miniCFG=6461, next=5175], Scope = 39

MiniCFG: UID 6474 CFBlock [
	t6465 = 0
	t6463 = j_s31 < t6465 {canonical: (j_s31) < (0)}
next=6476
], Scope = 33
UID 6476 CFEndOfMiniCFG [next=5175]

UID 5175 CFConditional [ miniCFG=6474, boolTemp=t6463, ifTrue=5174, ifFalse=5179], Scope = 33

MiniCFG: UID 6487 CFBlock [
	r_s31 = 0
	g_s31 = 0
	b_s31 = 0
next=6488
], Scope = 40
UID 6488 CFEndOfMiniCFG [next=5174]

UID 5174 CFBlock [ miniCFG=6487, next=5179], Scope = 40

MiniCFG: UID 6496 CFBlock [
	t6492 = 1
	row_s29 += t6492
next=6497
], Scope = 29
UID 6497 CFEndOfMiniCFG [next=5107]

UID 5107 CFBlock [ miniCFG=6496, next=5181], Scope = 29
UID 5182 CFReturn
----------
CFG for createUnsharpMaskH
----------

MiniCFG: UID 6580 CFBlock [
	center_s45 = 3
	r_s45 = 0
next=6581
], Scope = 45
UID 6581 CFEndOfMiniCFG [next=6503]

UID 6503 CFBlock [ miniCFG=6580, next=6532], Scope = 45

MiniCFG: UID 6591 CFBlock [
	t6582 = r_s45 < rows_s0 {canonical: (r_s45) < (rows_s0)}
next=6593
], Scope = 45
UID 6593 CFEndOfMiniCFG [next=6532]

UID 6532 CFConditional [ miniCFG=6591, boolTemp=t6582, ifTrue=6507, ifFalse=6535], Scope = 45

MiniCFG: UID 6598 CFBlock [
	c_s45 = 0
next=6599
], Scope = 46
UID 6599 CFEndOfMiniCFG [next=6507]

UID 6507 CFBlock [ miniCFG=6598, next=6511], Scope = 46

MiniCFG: UID 6609 CFBlock [
	t6600 = c_s45 < center_s45 {canonical: (c_s45) < (center_s45)}
next=6611
], Scope = 46
UID 6611 CFEndOfMiniCFG [next=6511]

UID 6511 CFConditional [ miniCFG=6609, boolTemp=t6600, ifTrue=6510, ifFalse=6513], Scope = 46

MiniCFG: UID 6668 CFBlock [
	t6617 = 731
	t6615 = r_s45 * t6617 {canonical: (r_s45) * (731)}
	t6614 = t6615 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t6638 = 2193
	t6636 = r_s45 * t6638 {canonical: (r_s45) * (2193)}
	t6650 = 3
	t6648 = c_s45 * t6650 {canonical: (c_s45) * (3)}
	t6635 = t6636 + t6648 {canonical: ((r_s45) * (2193)) + ((c_s45) * (3))}
	unsharpMask_s0[t6614] = image_s0[t6635]
next=6669
], Scope = 47
UID 6669 CFEndOfMiniCFG [next=6510]

UID 6510 CFBlock [ miniCFG=6668, next=6508], Scope = 47

MiniCFG: UID 6677 CFBlock [
	t6673 = 1
	c_s45 += t6673
next=6678
], Scope = 46
UID 6678 CFEndOfMiniCFG [next=6508]

UID 6508 CFBlock [ miniCFG=6677, next=6511], Scope = 46

MiniCFG: UID 6684 CFBlock [
	c_s45 = center_s45 {canonical: center_s45}
next=6685
], Scope = 46
UID 6685 CFEndOfMiniCFG [next=6513]

UID 6513 CFBlock [ miniCFG=6684, next=6525], Scope = 46

MiniCFG: UID 6704 CFBlock [
	t6688 = cols_s0 - center_s45 {canonical: (cols_s0) - (center_s45)}
	t6686 = c_s45 < t6688 {canonical: (c_s45) < ((cols_s0) - (center_s45))}
next=6706
], Scope = 46
UID 6706 CFEndOfMiniCFG [next=6525]

UID 6525 CFConditional [ miniCFG=6704, boolTemp=t6686, ifTrue=6524, ifFalse=6527], Scope = 46

MiniCFG: UID 7354 CFBlock [
	t6712 = 731
	t6710 = r_s45 * t6712 {canonical: (r_s45) * (731)}
	t6709 = t6710 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	unsharpMask_s0[t6709] = 0
	t6735 = 731
	t6733 = r_s45 * t6735 {canonical: (r_s45) * (731)}
	t6732 = t6733 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t6759 = 2193
	t6757 = r_s45 * t6759 {canonical: (r_s45) * (2193)}
	t6770 = 3
	t6769 = t6770 * c_s45 {canonical: (3) * (c_s45)}
	t6756 = t6757 + t6769 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	t6788 = 9
	t6755 = t6756 - t6788 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) - (9)}
	t6754 = image_s0[t6755]
	t6801 = 0
	t6800 = unsharpKernel_s0[t6801]
	t6753 = t6754 * t6800 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) - (9)]) * (unsharpKernel_s0[0])}
	unsharpMask_s0[t6732] += t6753
	t6818 = 731
	t6816 = r_s45 * t6818 {canonical: (r_s45) * (731)}
	t6815 = t6816 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t6842 = 2193
	t6840 = r_s45 * t6842 {canonical: (r_s45) * (2193)}
	t6853 = 3
	t6852 = t6853 * c_s45 {canonical: (3) * (c_s45)}
	t6839 = t6840 + t6852 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	t6871 = 6
	t6838 = t6839 - t6871 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) - (6)}
	t6837 = image_s0[t6838]
	t6884 = 1
	t6883 = unsharpKernel_s0[t6884]
	t6836 = t6837 * t6883 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) - (6)]) * (unsharpKernel_s0[1])}
	unsharpMask_s0[t6815] += t6836
	t6901 = 731
	t6899 = r_s45 * t6901 {canonical: (r_s45) * (731)}
	t6898 = t6899 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t6925 = 2193
	t6923 = r_s45 * t6925 {canonical: (r_s45) * (2193)}
	t6936 = 3
	t6935 = t6936 * c_s45 {canonical: (3) * (c_s45)}
	t6922 = t6923 + t6935 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	t6954 = 3
	t6921 = t6922 - t6954 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) - (3)}
	t6920 = image_s0[t6921]
	t6967 = 2
	t6966 = unsharpKernel_s0[t6967]
	t6919 = t6920 * t6966 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) - (3)]) * (unsharpKernel_s0[2])}
	unsharpMask_s0[t6898] += t6919
	t6984 = 731
	t6982 = r_s45 * t6984 {canonical: (r_s45) * (731)}
	t6981 = t6982 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7007 = 2193
	t7005 = r_s45 * t7007 {canonical: (r_s45) * (2193)}
	t7018 = 3
	t7017 = t7018 * c_s45 {canonical: (3) * (c_s45)}
	t7004 = t7005 + t7017 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	t7003 = image_s0[t7004]
	t7039 = 3
	t7038 = unsharpKernel_s0[t7039]
	t7002 = t7003 * t7038 {canonical: (image_s0[((r_s45) * (2193)) + ((3) * (c_s45))]) * (unsharpKernel_s0[3])}
	unsharpMask_s0[t6981] += t7002
	t7056 = 731
	t7054 = r_s45 * t7056 {canonical: (r_s45) * (731)}
	t7053 = t7054 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7080 = 2193
	t7078 = r_s45 * t7080 {canonical: (r_s45) * (2193)}
	t7091 = 3
	t7090 = t7091 * c_s45 {canonical: (3) * (c_s45)}
	t7077 = t7078 + t7090 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	t7109 = 3
	t7076 = t7077 + t7109 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) + (3)}
	t7075 = image_s0[t7076]
	t7122 = 4
	t7121 = unsharpKernel_s0[t7122]
	t7074 = t7075 * t7121 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) + (3)]) * (unsharpKernel_s0[4])}
	unsharpMask_s0[t7053] += t7074
	t7139 = 731
	t7137 = r_s45 * t7139 {canonical: (r_s45) * (731)}
	t7136 = t7137 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7163 = 2193
	t7161 = r_s45 * t7163 {canonical: (r_s45) * (2193)}
	t7174 = 3
	t7173 = t7174 * c_s45 {canonical: (3) * (c_s45)}
	t7160 = t7161 + t7173 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	t7192 = 6
	t7159 = t7160 + t7192 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) + (6)}
	t7158 = image_s0[t7159]
	t7205 = 5
	t7204 = unsharpKernel_s0[t7205]
	t7157 = t7158 * t7204 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) + (6)]) * (unsharpKernel_s0[5])}
	unsharpMask_s0[t7136] += t7157
	t7222 = 731
	t7220 = r_s45 * t7222 {canonical: (r_s45) * (731)}
	t7219 = t7220 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7246 = 2193
	t7244 = r_s45 * t7246 {canonical: (r_s45) * (2193)}
	t7257 = 3
	t7256 = t7257 * c_s45 {canonical: (3) * (c_s45)}
	t7243 = t7244 + t7256 {canonical: ((r_s45) * (2193)) + ((3) * (c_s45))}
	t7275 = 9
	t7242 = t7243 + t7275 {canonical: (((r_s45) * (2193)) + ((3) * (c_s45))) + (9)}
	t7241 = image_s0[t7242]
	t7288 = 6
	t7287 = unsharpKernel_s0[t7288]
	t7240 = t7241 * t7287 {canonical: (image_s0[(((r_s45) * (2193)) + ((3) * (c_s45))) + (9)]) * (unsharpKernel_s0[6])}
	unsharpMask_s0[t7219] += t7240
	t7305 = 731
	t7303 = r_s45 * t7305 {canonical: (r_s45) * (731)}
	t7302 = t7303 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7327 = 731
	t7325 = r_s45 * t7327 {canonical: (r_s45) * (731)}
	t7324 = t7325 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7323 = unsharpMask_s0[t7324]
	unsharpMask_s0[t7302] = t7323 / kernel_sum_s0 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) / (kernel_sum_s0)}
next=7355
], Scope = 48
UID 7355 CFEndOfMiniCFG [next=6524]

UID 6524 CFBlock [ miniCFG=7354, next=6514], Scope = 48

MiniCFG: UID 7363 CFBlock [
	t7359 = 1
	c_s45 += t7359
next=7364
], Scope = 46
UID 7364 CFEndOfMiniCFG [next=6514]

UID 6514 CFBlock [ miniCFG=7363, next=6525], Scope = 46

MiniCFG: UID 7376 CFBlock [
	c_s45 = cols_s0 - center_s45 {canonical: (cols_s0) - (center_s45)}
next=7377
], Scope = 46
UID 7377 CFEndOfMiniCFG [next=6527]

UID 6527 CFBlock [ miniCFG=7376, next=6531], Scope = 46

MiniCFG: UID 7387 CFBlock [
	t7378 = c_s45 < cols_s0 {canonical: (c_s45) < (cols_s0)}
next=7389
], Scope = 46
UID 7389 CFEndOfMiniCFG [next=6531]

UID 6531 CFConditional [ miniCFG=7387, boolTemp=t7378, ifTrue=6530, ifFalse=6504], Scope = 46

MiniCFG: UID 7446 CFBlock [
	t7395 = 731
	t7393 = r_s45 * t7395 {canonical: (r_s45) * (731)}
	t7392 = t7393 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7416 = 2193
	t7414 = r_s45 * t7416 {canonical: (r_s45) * (2193)}
	t7428 = 3
	t7426 = c_s45 * t7428 {canonical: (c_s45) * (3)}
	t7413 = t7414 + t7426 {canonical: ((r_s45) * (2193)) + ((c_s45) * (3))}
	unsharpMask_s0[t7392] = image_s0[t7413]
next=7447
], Scope = 49
UID 7447 CFEndOfMiniCFG [next=6530]

UID 6530 CFBlock [ miniCFG=7446, next=6528], Scope = 49

MiniCFG: UID 7455 CFBlock [
	t7451 = 1
	c_s45 += t7451
next=7456
], Scope = 46
UID 7456 CFEndOfMiniCFG [next=6528]

UID 6528 CFBlock [ miniCFG=7455, next=6531], Scope = 46

MiniCFG: UID 7464 CFBlock [
	t7460 = 1
	r_s45 += t7460
next=7465
], Scope = 45
UID 7465 CFEndOfMiniCFG [next=6504]

UID 6504 CFBlock [ miniCFG=7464, next=6532], Scope = 45

MiniCFG: UID 7489 CFBlock [
	t7468 = center_s45 {canonical: center_s45}
	t7473 = center_s45 {canonical: center_s45}
	t7472 = unsharpKernel_s0[t7473]
	unsharpKernel_s0[t7468] = t7472 - kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s45]) - (kernel_sum_s0)}
	c_s45 = 0
next=7490
], Scope = 45
UID 7490 CFEndOfMiniCFG [next=6535]

UID 6535 CFBlock [ miniCFG=7489, next=6570], Scope = 45

MiniCFG: UID 7500 CFBlock [
	t7491 = c_s45 < cols_s0 {canonical: (c_s45) < (cols_s0)}
next=7502
], Scope = 45
UID 7502 CFEndOfMiniCFG [next=6570]

UID 6570 CFConditional [ miniCFG=7500, boolTemp=t7491, ifTrue=6542, ifFalse=6571], Scope = 45

MiniCFG: UID 7544 CFBlock [
	t7506 = c_s45 {canonical: c_s45}
	m1_s50 = unsharpMask_s0[t7506]
	t7515 = 731
	t7513 = c_s45 + t7515 {canonical: (c_s45) + (731)}
	m2_s50 = unsharpMask_s0[t7513]
	t7530 = 1462
	t7528 = c_s45 + t7530 {canonical: (c_s45) + (1462)}
	m3_s50 = unsharpMask_s0[t7528]
	r_s45 = 0
next=7545
], Scope = 50
UID 7545 CFEndOfMiniCFG [next=6542]

UID 6542 CFBlock [ miniCFG=7544, next=6546], Scope = 50

MiniCFG: UID 7555 CFBlock [
	t7546 = r_s45 < center_s45 {canonical: (r_s45) < (center_s45)}
next=7557
], Scope = 50
UID 7557 CFEndOfMiniCFG [next=6546]

UID 6546 CFConditional [ miniCFG=7555, boolTemp=t7546, ifTrue=6545, ifFalse=6548], Scope = 50

MiniCFG: UID 7582 CFBlock [
	t7563 = 731
	t7561 = r_s45 * t7563 {canonical: (r_s45) * (731)}
	t7560 = t7561 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	unsharpMask_s0[t7560] = 0
next=7583
], Scope = 51
UID 7583 CFEndOfMiniCFG [next=6545]

UID 6545 CFBlock [ miniCFG=7582, next=6543], Scope = 51

MiniCFG: UID 7591 CFBlock [
	t7587 = 1
	r_s45 += t7587
next=7592
], Scope = 50
UID 7592 CFEndOfMiniCFG [next=6543]

UID 6543 CFBlock [ miniCFG=7591, next=6546], Scope = 50

MiniCFG: UID 7598 CFBlock [
	r_s45 = center_s45 {canonical: center_s45}
next=7599
], Scope = 50
UID 7599 CFEndOfMiniCFG [next=6548]

UID 6548 CFBlock [ miniCFG=7598, next=6563], Scope = 50

MiniCFG: UID 7618 CFBlock [
	t7602 = rows_s0 - center_s45 {canonical: (rows_s0) - (center_s45)}
	t7600 = r_s45 < t7602 {canonical: (r_s45) < ((rows_s0) - (center_s45))}
next=7620
], Scope = 50
UID 7620 CFEndOfMiniCFG [next=6563]

UID 6563 CFConditional [ miniCFG=7618, boolTemp=t7600, ifTrue=6562, ifFalse=6565], Scope = 50

MiniCFG: UID 8162 CFBlock [
	dot_s52 = 0
	t7632 = 731
	t7630 = r_s45 * t7632 {canonical: (r_s45) * (731)}
	t7629 = t7630 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7628 = unsharpMask_s0[t7629]
	t7655 = 0
	t7654 = unsharpKernel_s0[t7655]
	t7652 = m1_s50 * t7654 {canonical: (m1_s50) * (unsharpKernel_s0[0])}
	t7627 = t7628 + t7652 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((m1_s50) * (unsharpKernel_s0[0]))}
	dot_s52 += t7627
	t7682 = 731
	t7680 = r_s45 * t7682 {canonical: (r_s45) * (731)}
	t7679 = t7680 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7678 = unsharpMask_s0[t7679]
	t7705 = 1
	t7704 = unsharpKernel_s0[t7705]
	t7702 = m2_s50 * t7704 {canonical: (m2_s50) * (unsharpKernel_s0[1])}
	t7677 = t7678 + t7702 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((m2_s50) * (unsharpKernel_s0[1]))}
	dot_s52 += t7677
	t7732 = 731
	t7730 = r_s45 * t7732 {canonical: (r_s45) * (731)}
	t7729 = t7730 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7728 = unsharpMask_s0[t7729]
	t7755 = 2
	t7754 = unsharpKernel_s0[t7755]
	t7752 = m3_s50 * t7754 {canonical: (m3_s50) * (unsharpKernel_s0[2])}
	t7727 = t7728 + t7752 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((m3_s50) * (unsharpKernel_s0[2]))}
	dot_s52 += t7727
	t7782 = 731
	t7780 = r_s45 * t7782 {canonical: (r_s45) * (731)}
	t7779 = t7780 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7778 = unsharpMask_s0[t7779]
	t7806 = 731
	t7805 = t7806 * r_s45 {canonical: (731) * (r_s45)}
	t7804 = t7805 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	t7803 = unsharpMask_s0[t7804]
	t7828 = 3
	t7827 = unsharpKernel_s0[t7828]
	t7802 = t7803 * t7827 {canonical: (unsharpMask_s0[((731) * (r_s45)) + (c_s45)]) * (unsharpKernel_s0[3])}
	t7777 = t7778 + t7802 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[((731) * (r_s45)) + (c_s45)]) * (unsharpKernel_s0[3]))}
	dot_s52 += t7777
	t7855 = 731
	t7853 = r_s45 * t7855 {canonical: (r_s45) * (731)}
	t7852 = t7853 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7851 = unsharpMask_s0[t7852]
	t7880 = 731
	t7879 = t7880 * r_s45 {canonical: (731) * (r_s45)}
	t7878 = t7879 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	t7899 = 731
	t7877 = t7878 + t7899 {canonical: (((731) * (r_s45)) + (c_s45)) + (731)}
	t7876 = unsharpMask_s0[t7877]
	t7912 = 4
	t7911 = unsharpKernel_s0[t7912]
	t7875 = t7876 * t7911 {canonical: (unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (731)]) * (unsharpKernel_s0[4])}
	t7850 = t7851 + t7875 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (731)]) * (unsharpKernel_s0[4]))}
	dot_s52 += t7850
	t7939 = 731
	t7937 = r_s45 * t7939 {canonical: (r_s45) * (731)}
	t7936 = t7937 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t7935 = unsharpMask_s0[t7936]
	t7964 = 731
	t7963 = t7964 * r_s45 {canonical: (731) * (r_s45)}
	t7962 = t7963 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	t7983 = 1462
	t7961 = t7962 + t7983 {canonical: (((731) * (r_s45)) + (c_s45)) + (1462)}
	t7960 = unsharpMask_s0[t7961]
	t7996 = 5
	t7995 = unsharpKernel_s0[t7996]
	t7959 = t7960 * t7995 {canonical: (unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (1462)]) * (unsharpKernel_s0[5])}
	t7934 = t7935 + t7959 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (1462)]) * (unsharpKernel_s0[5]))}
	dot_s52 += t7934
	t8023 = 731
	t8021 = r_s45 * t8023 {canonical: (r_s45) * (731)}
	t8020 = t8021 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	t8019 = unsharpMask_s0[t8020]
	t8048 = 731
	t8047 = t8048 * r_s45 {canonical: (731) * (r_s45)}
	t8046 = t8047 + c_s45 {canonical: ((731) * (r_s45)) + (c_s45)}
	t8067 = 2193
	t8045 = t8046 + t8067 {canonical: (((731) * (r_s45)) + (c_s45)) + (2193)}
	t8044 = unsharpMask_s0[t8045]
	t8080 = 6
	t8079 = unsharpKernel_s0[t8080]
	t8043 = t8044 * t8079 {canonical: (unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (2193)]) * (unsharpKernel_s0[6])}
	t8018 = t8019 + t8043 {canonical: (unsharpMask_s0[((r_s45) * (731)) + (c_s45)]) + ((unsharpMask_s0[(((731) * (r_s45)) + (c_s45)) + (2193)]) * (unsharpKernel_s0[6]))}
	dot_s52 += t8018
	m1_s50 = m2_s50 {canonical: m2_s50}
	m2_s50 = m3_s50 {canonical: m3_s50}
	t8113 = 731
	t8111 = r_s45 * t8113 {canonical: (r_s45) * (731)}
	t8110 = t8111 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	m3_s50 = unsharpMask_s0[t8110]
	t8136 = 731
	t8134 = r_s45 * t8136 {canonical: (r_s45) * (731)}
	t8133 = t8134 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	unsharpMask_s0[t8133] = dot_s52 / kernel_sum_s0 {canonical: (dot_s52) / (kernel_sum_s0)}
next=8163
], Scope = 52
UID 8163 CFEndOfMiniCFG [next=6562]

UID 6562 CFBlock [ miniCFG=8162, next=6549], Scope = 52

MiniCFG: UID 8171 CFBlock [
	t8167 = 1
	r_s45 += t8167
next=8172
], Scope = 50
UID 8172 CFEndOfMiniCFG [next=6549]

UID 6549 CFBlock [ miniCFG=8171, next=6563], Scope = 50

MiniCFG: UID 8184 CFBlock [
	r_s45 = rows_s0 - center_s45 {canonical: (rows_s0) - (center_s45)}
next=8185
], Scope = 50
UID 8185 CFEndOfMiniCFG [next=6565]

UID 6565 CFBlock [ miniCFG=8184, next=6569], Scope = 50

MiniCFG: UID 8195 CFBlock [
	t8186 = r_s45 < rows_s0 {canonical: (r_s45) < (rows_s0)}
next=8197
], Scope = 50
UID 8197 CFEndOfMiniCFG [next=6569]

UID 6569 CFConditional [ miniCFG=8195, boolTemp=t8186, ifTrue=6568, ifFalse=6536], Scope = 50

MiniCFG: UID 8222 CFBlock [
	t8203 = 731
	t8201 = r_s45 * t8203 {canonical: (r_s45) * (731)}
	t8200 = t8201 + c_s45 {canonical: ((r_s45) * (731)) + (c_s45)}
	unsharpMask_s0[t8200] = 0
next=8223
], Scope = 53
UID 8223 CFEndOfMiniCFG [next=6568]

UID 6568 CFBlock [ miniCFG=8222, next=6566], Scope = 53

MiniCFG: UID 8231 CFBlock [
	t8227 = 1
	r_s45 += t8227
next=8232
], Scope = 50
UID 8232 CFEndOfMiniCFG [next=6566]

UID 6566 CFBlock [ miniCFG=8231, next=6569], Scope = 50

MiniCFG: UID 8240 CFBlock [
	t8236 = 1
	c_s45 += t8236
next=8241
], Scope = 45
UID 8241 CFEndOfMiniCFG [next=6536]

UID 6536 CFBlock [ miniCFG=8240, next=6570], Scope = 45

MiniCFG: UID 8262 CFBlock [
	t8244 = center_s45 {canonical: center_s45}
	t8249 = center_s45 {canonical: center_s45}
	t8248 = unsharpKernel_s0[t8249]
	unsharpKernel_s0[t8244] = t8248 + kernel_sum_s0 {canonical: (unsharpKernel_s0[center_s45]) + (kernel_sum_s0)}
next=8263
], Scope = 45
UID 8263 CFEndOfMiniCFG [next=6571]

UID 6571 CFBlock [ miniCFG=8262, next=6572], Scope = 45
UID 6572 CFReturn
----------
CFG for read_file
----------

MiniCFG: UID 8304 CFBlock [
	ppm_open_for_read_s0["data/input.ppm"]
	ppm_get_cols_s0[]
	cols_s0 = load %rax
	ppm_get_rows_s0[]
	rows_s0 = load %rax
	r_s2 = 0
next=8305
], Scope = 2
UID 8305 CFEndOfMiniCFG [next=8271]

UID 8271 CFBlock [ miniCFG=8304, next=8282], Scope = 2

MiniCFG: UID 8315 CFBlock [
	t8306 = r_s2 < rows_s0 {canonical: (r_s2) < (rows_s0)}
next=8317
], Scope = 2
UID 8317 CFEndOfMiniCFG [next=8282]

UID 8282 CFConditional [ miniCFG=8315, boolTemp=t8306, ifTrue=8275, ifFalse=8283], Scope = 2

MiniCFG: UID 8322 CFBlock [
	c_s2 = 0
next=8323
], Scope = 3
UID 8323 CFEndOfMiniCFG [next=8275]

UID 8275 CFBlock [ miniCFG=8322, next=8281], Scope = 3

MiniCFG: UID 8333 CFBlock [
	t8324 = c_s2 < cols_s0 {canonical: (c_s2) < (cols_s0)}
next=8335
], Scope = 3
UID 8335 CFEndOfMiniCFG [next=8281]

UID 8281 CFConditional [ miniCFG=8333, boolTemp=t8324, ifTrue=8280, ifFalse=8272], Scope = 3

MiniCFG: UID 8481 CFBlock [
	t8342 = 2193
	t8340 = r_s2 * t8342 {canonical: (r_s2) * (2193)}
	t8354 = 3
	t8352 = c_s2 * t8354 {canonical: (c_s2) * (3)}
	t8339 = t8340 + t8352 {canonical: ((r_s2) * (2193)) + ((c_s2) * (3))}
	t8371 = 0
	t8338 = t8339 + t8371 {canonical: (((r_s2) * (2193)) + ((c_s2) * (3))) + (0)}
	ppm_get_next_pixel_color_s0[]
	image_s0[t8338] = load %rax
	t8390 = 2193
	t8388 = r_s2 * t8390 {canonical: (r_s2) * (2193)}
	t8402 = 3
	t8400 = c_s2 * t8402 {canonical: (c_s2) * (3)}
	t8387 = t8388 + t8400 {canonical: ((r_s2) * (2193)) + ((c_s2) * (3))}
	t8419 = 1
	t8386 = t8387 + t8419 {canonical: (((r_s2) * (2193)) + ((c_s2) * (3))) + (1)}
	ppm_get_next_pixel_color_s0[]
	image_s0[t8386] = load %rax
	t8438 = 2193
	t8436 = r_s2 * t8438 {canonical: (r_s2) * (2193)}
	t8450 = 3
	t8448 = c_s2 * t8450 {canonical: (c_s2) * (3)}
	t8435 = t8436 + t8448 {canonical: ((r_s2) * (2193)) + ((c_s2) * (3))}
	t8467 = 2
	t8434 = t8435 + t8467 {canonical: (((r_s2) * (2193)) + ((c_s2) * (3))) + (2)}
	ppm_get_next_pixel_color_s0[]
	image_s0[t8434] = load %rax
next=8482
], Scope = 4
UID 8482 CFEndOfMiniCFG [next=8280]

UID 8280 CFBlock [ miniCFG=8481, next=8276], Scope = 4

MiniCFG: UID 8490 CFBlock [
	t8486 = 1
	c_s2 += t8486
next=8491
], Scope = 3
UID 8491 CFEndOfMiniCFG [next=8276]

UID 8276 CFBlock [ miniCFG=8490, next=8281], Scope = 3

MiniCFG: UID 8499 CFBlock [
	t8495 = 1
	r_s2 += t8495
next=8500
], Scope = 2
UID 8500 CFEndOfMiniCFG [next=8272]

UID 8272 CFBlock [ miniCFG=8499, next=8282], Scope = 2

MiniCFG: UID 8505 CFBlock [
	ppm_close_s0[]
next=8506
], Scope = 2
UID 8506 CFEndOfMiniCFG [next=8283]

UID 8283 CFBlock [ miniCFG=8505, next=8284], Scope = 2
UID 8284 CFReturn
----------
CFG for levels
----------

MiniCFG: UID 8563 CFBlock [
	b_s90 = 10
	w_s90 = 243
	r_s90 = 0
next=8564
], Scope = 90
UID 8564 CFEndOfMiniCFG [next=8513]

UID 8513 CFBlock [ miniCFG=8563, next=8551], Scope = 90

MiniCFG: UID 8574 CFBlock [
	t8565 = r_s90 < rows_s0 {canonical: (r_s90) < (rows_s0)}
next=8576
], Scope = 90
UID 8576 CFEndOfMiniCFG [next=8551]

UID 8551 CFConditional [ miniCFG=8574, boolTemp=t8565, ifTrue=8517, ifFalse=8552], Scope = 90

MiniCFG: UID 8581 CFBlock [
	c_s90 = 0
next=8582
], Scope = 91
UID 8582 CFEndOfMiniCFG [next=8517]

UID 8517 CFBlock [ miniCFG=8581, next=8550], Scope = 91

MiniCFG: UID 8592 CFBlock [
	t8583 = c_s90 < cols_s0 {canonical: (c_s90) < (cols_s0)}
next=8594
], Scope = 91
UID 8594 CFEndOfMiniCFG [next=8550]

UID 8550 CFConditional [ miniCFG=8592, boolTemp=t8583, ifTrue=8520, ifFalse=8514], Scope = 91

MiniCFG: UID 8700 CFBlock [
	t8600 = 2193
	t8598 = r_s90 * t8600 {canonical: (r_s90) * (2193)}
	t8612 = 3
	t8610 = c_s90 * t8612 {canonical: (c_s90) * (3)}
	t8597 = t8598 + t8610 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t8635 = 2193
	t8633 = r_s90 * t8635 {canonical: (r_s90) * (2193)}
	t8647 = 3
	t8645 = c_s90 * t8647 {canonical: (c_s90) * (3)}
	t8632 = t8633 + t8645 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t8631 = image_s0[t8632]
	t8630 = t8631 - b_s90 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) - (b_s90)}
	t8674 = 255
	t8629 = t8630 * t8674 {canonical: ((image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) - (b_s90)) * (255)}
	t8684 = w_s90 - b_s90 {canonical: (w_s90) - (b_s90)}
	image_s0[t8597] = t8629 / t8684 {canonical: (((image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) - (b_s90)) * (255)) / ((w_s90) - (b_s90))}
next=8701
], Scope = 92
UID 8701 CFEndOfMiniCFG [next=8520]

UID 8520 CFBlock [ miniCFG=8700, next=8529], Scope = 92

MiniCFG: UID 8747 CFBlock [
	t8707 = 2193
	t8705 = r_s90 * t8707 {canonical: (r_s90) * (2193)}
	t8719 = 3
	t8717 = c_s90 * t8719 {canonical: (c_s90) * (3)}
	t8704 = t8705 + t8717 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t8703 = image_s0[t8704]
	t8738 = 0
	t8702 = t8703 < t8738 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) < (0)}
next=8749
], Scope = 92
UID 8749 CFEndOfMiniCFG [next=8529]

UID 8529 CFConditional [ miniCFG=8747, boolTemp=t8702, ifTrue=8523, ifFalse=8528], Scope = 92

MiniCFG: UID 8785 CFBlock [
	t8755 = 2193
	t8753 = r_s90 * t8755 {canonical: (r_s90) * (2193)}
	t8767 = 3
	t8765 = c_s90 * t8767 {canonical: (c_s90) * (3)}
	t8752 = t8753 + t8765 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	image_s0[t8752] = 0
next=8786
], Scope = 93
UID 8786 CFEndOfMiniCFG [next=8523]

UID 8523 CFBlock [ miniCFG=8785, next=8530], Scope = 93

MiniCFG: UID 8914 CFBlock [
	t8793 = 2193
	t8791 = r_s90 * t8793 {canonical: (r_s90) * (2193)}
	t8805 = 3
	t8803 = c_s90 * t8805 {canonical: (c_s90) * (3)}
	t8790 = t8791 + t8803 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t8822 = 1
	t8789 = t8790 + t8822 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	t8839 = 2193
	t8837 = r_s90 * t8839 {canonical: (r_s90) * (2193)}
	t8851 = 3
	t8849 = c_s90 * t8851 {canonical: (c_s90) * (3)}
	t8836 = t8837 + t8849 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t8868 = 1
	t8835 = t8836 + t8868 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	t8834 = image_s0[t8835]
	t8833 = t8834 - b_s90 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) - (b_s90)}
	t8888 = 255
	t8832 = t8833 * t8888 {canonical: ((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) - (b_s90)) * (255)}
	t8898 = w_s90 - b_s90 {canonical: (w_s90) - (b_s90)}
	image_s0[t8789] = t8832 / t8898 {canonical: (((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) - (b_s90)) * (255)) / ((w_s90) - (b_s90))}
next=8915
], Scope = 92
UID 8915 CFEndOfMiniCFG [next=8530]

UID 8530 CFBlock [ miniCFG=8914, next=8539], Scope = 92

MiniCFG: UID 8972 CFBlock [
	t8922 = 2193
	t8920 = r_s90 * t8922 {canonical: (r_s90) * (2193)}
	t8934 = 3
	t8932 = c_s90 * t8934 {canonical: (c_s90) * (3)}
	t8919 = t8920 + t8932 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t8951 = 1
	t8918 = t8919 + t8951 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	t8917 = image_s0[t8918]
	t8963 = 0
	t8916 = t8917 < t8963 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) < (0)}
next=8974
], Scope = 92
UID 8974 CFEndOfMiniCFG [next=8539]

UID 8539 CFConditional [ miniCFG=8972, boolTemp=t8916, ifTrue=8533, ifFalse=8538], Scope = 92

MiniCFG: UID 9021 CFBlock [
	t8981 = 2193
	t8979 = r_s90 * t8981 {canonical: (r_s90) * (2193)}
	t8993 = 3
	t8991 = c_s90 * t8993 {canonical: (c_s90) * (3)}
	t8978 = t8979 + t8991 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9010 = 1
	t8977 = t8978 + t9010 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	image_s0[t8977] = 0
next=9022
], Scope = 96
UID 9022 CFEndOfMiniCFG [next=8533]

UID 8533 CFBlock [ miniCFG=9021, next=8540], Scope = 96

MiniCFG: UID 9150 CFBlock [
	t9029 = 2193
	t9027 = r_s90 * t9029 {canonical: (r_s90) * (2193)}
	t9041 = 3
	t9039 = c_s90 * t9041 {canonical: (c_s90) * (3)}
	t9026 = t9027 + t9039 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9058 = 2
	t9025 = t9026 + t9058 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	t9075 = 2193
	t9073 = r_s90 * t9075 {canonical: (r_s90) * (2193)}
	t9087 = 3
	t9085 = c_s90 * t9087 {canonical: (c_s90) * (3)}
	t9072 = t9073 + t9085 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9104 = 2
	t9071 = t9072 + t9104 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	t9070 = image_s0[t9071]
	t9069 = t9070 - b_s90 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) - (b_s90)}
	t9124 = 255
	t9068 = t9069 * t9124 {canonical: ((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) - (b_s90)) * (255)}
	t9134 = w_s90 - b_s90 {canonical: (w_s90) - (b_s90)}
	image_s0[t9025] = t9068 / t9134 {canonical: (((image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) - (b_s90)) * (255)) / ((w_s90) - (b_s90))}
next=9151
], Scope = 92
UID 9151 CFEndOfMiniCFG [next=8540]

UID 8540 CFBlock [ miniCFG=9150, next=8549], Scope = 92

MiniCFG: UID 9208 CFBlock [
	t9158 = 2193
	t9156 = r_s90 * t9158 {canonical: (r_s90) * (2193)}
	t9170 = 3
	t9168 = c_s90 * t9170 {canonical: (c_s90) * (3)}
	t9155 = t9156 + t9168 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9187 = 2
	t9154 = t9155 + t9187 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	t9153 = image_s0[t9154]
	t9199 = 0
	t9152 = t9153 < t9199 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) < (0)}
next=9210
], Scope = 92
UID 9210 CFEndOfMiniCFG [next=8549]

UID 8549 CFConditional [ miniCFG=9208, boolTemp=t9152, ifTrue=8543, ifFalse=8548], Scope = 92

MiniCFG: UID 9257 CFBlock [
	t9217 = 2193
	t9215 = r_s90 * t9217 {canonical: (r_s90) * (2193)}
	t9229 = 3
	t9227 = c_s90 * t9229 {canonical: (c_s90) * (3)}
	t9214 = t9215 + t9227 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9246 = 2
	t9213 = t9214 + t9246 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	image_s0[t9213] = 0
next=9258
], Scope = 99
UID 9258 CFEndOfMiniCFG [next=8543]

UID 8543 CFBlock [ miniCFG=9257, next=8518], Scope = 99

MiniCFG: UID 9266 CFBlock [
	t9262 = 1
	c_s90 += t9262
next=9267
], Scope = 91
UID 9267 CFEndOfMiniCFG [next=8518]

UID 8518 CFBlock [ miniCFG=9266, next=8550], Scope = 91

MiniCFG: UID 9324 CFBlock [
	t9274 = 2193
	t9272 = r_s90 * t9274 {canonical: (r_s90) * (2193)}
	t9286 = 3
	t9284 = c_s90 * t9286 {canonical: (c_s90) * (3)}
	t9271 = t9272 + t9284 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9303 = 2
	t9270 = t9271 + t9303 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	t9269 = image_s0[t9270]
	t9315 = 255
	t9268 = t9269 > t9315 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (2)]) > (255)}
next=9326
], Scope = 100
UID 9326 CFEndOfMiniCFG [next=8548]

UID 8548 CFConditional [ miniCFG=9324, boolTemp=t9268, ifTrue=8547, ifFalse=8518], Scope = 100

MiniCFG: UID 9373 CFBlock [
	t9333 = 2193
	t9331 = r_s90 * t9333 {canonical: (r_s90) * (2193)}
	t9345 = 3
	t9343 = c_s90 * t9345 {canonical: (c_s90) * (3)}
	t9330 = t9331 + t9343 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9362 = 2
	t9329 = t9330 + t9362 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (2)}
	image_s0[t9329] = 255
next=9374
], Scope = 101
UID 9374 CFEndOfMiniCFG [next=8547]

UID 8547 CFBlock [ miniCFG=9373, next=8518], Scope = 101

MiniCFG: UID 9431 CFBlock [
	t9381 = 2193
	t9379 = r_s90 * t9381 {canonical: (r_s90) * (2193)}
	t9393 = 3
	t9391 = c_s90 * t9393 {canonical: (c_s90) * (3)}
	t9378 = t9379 + t9391 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9410 = 1
	t9377 = t9378 + t9410 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	t9376 = image_s0[t9377]
	t9422 = 255
	t9375 = t9376 > t9422 {canonical: (image_s0[(((r_s90) * (2193)) + ((c_s90) * (3))) + (1)]) > (255)}
next=9433
], Scope = 97
UID 9433 CFEndOfMiniCFG [next=8538]

UID 8538 CFConditional [ miniCFG=9431, boolTemp=t9375, ifTrue=8537, ifFalse=8540], Scope = 97

MiniCFG: UID 9480 CFBlock [
	t9440 = 2193
	t9438 = r_s90 * t9440 {canonical: (r_s90) * (2193)}
	t9452 = 3
	t9450 = c_s90 * t9452 {canonical: (c_s90) * (3)}
	t9437 = t9438 + t9450 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9469 = 1
	t9436 = t9437 + t9469 {canonical: (((r_s90) * (2193)) + ((c_s90) * (3))) + (1)}
	image_s0[t9436] = 255
next=9481
], Scope = 98
UID 9481 CFEndOfMiniCFG [next=8537]

UID 8537 CFBlock [ miniCFG=9480, next=8540], Scope = 98

MiniCFG: UID 9527 CFBlock [
	t9487 = 2193
	t9485 = r_s90 * t9487 {canonical: (r_s90) * (2193)}
	t9499 = 3
	t9497 = c_s90 * t9499 {canonical: (c_s90) * (3)}
	t9484 = t9485 + t9497 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	t9483 = image_s0[t9484]
	t9518 = 255
	t9482 = t9483 > t9518 {canonical: (image_s0[((r_s90) * (2193)) + ((c_s90) * (3))]) > (255)}
next=9529
], Scope = 94
UID 9529 CFEndOfMiniCFG [next=8528]

UID 8528 CFConditional [ miniCFG=9527, boolTemp=t9482, ifTrue=8527, ifFalse=8530], Scope = 94

MiniCFG: UID 9565 CFBlock [
	t9535 = 2193
	t9533 = r_s90 * t9535 {canonical: (r_s90) * (2193)}
	t9547 = 3
	t9545 = c_s90 * t9547 {canonical: (c_s90) * (3)}
	t9532 = t9533 + t9545 {canonical: ((r_s90) * (2193)) + ((c_s90) * (3))}
	image_s0[t9532] = 255
next=9566
], Scope = 95
UID 9566 CFEndOfMiniCFG [next=8527]

UID 8527 CFBlock [ miniCFG=9565, next=8530], Scope = 95

MiniCFG: UID 9574 CFBlock [
	t9570 = 1
	r_s90 += t9570
next=9575
], Scope = 90
UID 9575 CFEndOfMiniCFG [next=8514]

UID 8514 CFBlock [ miniCFG=9574, next=8551], Scope = 90
UID 8552 CFReturn
----------
CFG for convert2HSV
----------

MiniCFG: UID 9651 CFBlock [
	row_s10 = 0
next=9652
], Scope = 10
UID 9652 CFEndOfMiniCFG [next=9580]

UID 9580 CFBlock [ miniCFG=9651, next=9645], Scope = 10

MiniCFG: UID 9662 CFBlock [
	t9653 = row_s10 < rows_s0 {canonical: (row_s10) < (rows_s0)}
next=9664
], Scope = 10
UID 9664 CFEndOfMiniCFG [next=9645]

UID 9645 CFConditional [ miniCFG=9662, boolTemp=t9653, ifTrue=9584, ifFalse=9646], Scope = 10

MiniCFG: UID 9669 CFBlock [
	col_s10 = 0
next=9670
], Scope = 11
UID 9670 CFEndOfMiniCFG [next=9584]

UID 9584 CFBlock [ miniCFG=9669, next=9644], Scope = 11

MiniCFG: UID 9680 CFBlock [
	t9671 = col_s10 < cols_s0 {canonical: (col_s10) < (cols_s0)}
next=9682
], Scope = 11
UID 9682 CFEndOfMiniCFG [next=9644]

UID 9644 CFConditional [ miniCFG=9680, boolTemp=t9671, ifTrue=9592, ifFalse=9581], Scope = 11

MiniCFG: UID 9702 CFBlock [
	min_s12 = 0
	max_s12 = 0
	delta_s12 = 0
	h_s12 = 0
	s_s12 = 0
	v_s12 = 0
next=9703
], Scope = 12
UID 9703 CFEndOfMiniCFG [next=9592]

UID 9592 CFBlock [ miniCFG=9702, next=9600], Scope = 12

MiniCFG: UID 9803 CFBlock [
	t9710 = 2193
	t9708 = row_s10 * t9710 {canonical: (row_s10) * (2193)}
	t9722 = 3
	t9720 = col_s10 * t9722 {canonical: (col_s10) * (3)}
	t9707 = t9708 + t9720 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t9739 = 0
	t9706 = t9707 + t9739 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	t9705 = image_s0[t9706]
	t9756 = 2193
	t9754 = row_s10 * t9756 {canonical: (row_s10) * (2193)}
	t9768 = 3
	t9766 = col_s10 * t9768 {canonical: (col_s10) * (3)}
	t9753 = t9754 + t9766 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t9785 = 1
	t9752 = t9753 + t9785 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	t9751 = image_s0[t9752]
	t9704 = t9705 > t9751 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) > (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
next=9805
], Scope = 12
UID 9805 CFEndOfMiniCFG [next=9600]

UID 9600 CFConditional [ miniCFG=9803, boolTemp=t9704, ifTrue=9596, ifFalse=9599], Scope = 12

MiniCFG: UID 9899 CFBlock [
	t9813 = 2193
	t9811 = row_s10 * t9813 {canonical: (row_s10) * (2193)}
	t9825 = 3
	t9823 = col_s10 * t9825 {canonical: (col_s10) * (3)}
	t9810 = t9811 + t9823 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t9842 = 0
	t9809 = t9810 + t9842 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	max_s12 = image_s0[t9809]
	t9859 = 2193
	t9857 = row_s10 * t9859 {canonical: (row_s10) * (2193)}
	t9871 = 3
	t9869 = col_s10 * t9871 {canonical: (col_s10) * (3)}
	t9856 = t9857 + t9869 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t9888 = 1
	t9855 = t9856 + t9888 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	min_s12 = image_s0[t9855]
next=9900
], Scope = 13
UID 9900 CFEndOfMiniCFG [next=9596]

UID 9596 CFBlock [ miniCFG=9899, next=9609], Scope = 13

MiniCFG: UID 9955 CFBlock [
	t9908 = 2193
	t9906 = row_s10 * t9908 {canonical: (row_s10) * (2193)}
	t9920 = 3
	t9918 = col_s10 * t9920 {canonical: (col_s10) * (3)}
	t9905 = t9906 + t9918 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t9937 = 2
	t9904 = t9905 + t9937 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	t9903 = image_s0[t9904]
	t9901 = max_s12 < t9903 {canonical: (max_s12) < (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
next=9957
], Scope = 12
UID 9957 CFEndOfMiniCFG [next=9609]

UID 9609 CFConditional [ miniCFG=9955, boolTemp=t9901, ifTrue=9603, ifFalse=9608], Scope = 12

MiniCFG: UID 10005 CFBlock [
	t9965 = 2193
	t9963 = row_s10 * t9965 {canonical: (row_s10) * (2193)}
	t9977 = 3
	t9975 = col_s10 * t9977 {canonical: (col_s10) * (3)}
	t9962 = t9963 + t9975 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t9994 = 2
	t9961 = t9962 + t9994 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	max_s12 = image_s0[t9961]
next=10006
], Scope = 15
UID 10006 CFEndOfMiniCFG [next=9603]

UID 9603 CFBlock [ miniCFG=10005, next=9611], Scope = 15

MiniCFG: UID 10030 CFBlock [
	delta_s12 = max_s12 - min_s12 {canonical: (max_s12) - (min_s12)}
	t10020 = 4
	v_s12 = t10020 * max_s12 {canonical: (4) * (max_s12)}
next=10031
], Scope = 12
UID 10031 CFEndOfMiniCFG [next=9611]

UID 9611 CFBlock [ miniCFG=10030, next=9617], Scope = 12

MiniCFG: UID 10043 CFBlock [
	t10034 = 0
	t10032 = max_s12 == t10034 {canonical: (max_s12) == (0)}
next=10045
], Scope = 12
UID 10045 CFEndOfMiniCFG [next=9617]

UID 9617 CFConditional [ miniCFG=10043, boolTemp=t10032, ifTrue=9614, ifFalse=9616], Scope = 12

MiniCFG: UID 10050 CFBlock [
	s_s12 = 0
next=10051
], Scope = 18
UID 10051 CFEndOfMiniCFG [next=9614]

UID 9614 CFBlock [ miniCFG=10050, next=9640], Scope = 18

MiniCFG: UID 10063 CFBlock [
	t10054 = 0
	t10052 = delta_s12 == t10054 {canonical: (delta_s12) == (0)}
next=10065
], Scope = 12
UID 10065 CFEndOfMiniCFG [next=9640]

UID 9640 CFConditional [ miniCFG=10063, boolTemp=t10052, ifTrue=9620, ifFalse=9639], Scope = 12

MiniCFG: UID 10073 CFBlock [
	t10069 = 1
	h_s12 = -t10069
next=10074
], Scope = 20
UID 10074 CFEndOfMiniCFG [next=9620]

UID 9620 CFBlock [ miniCFG=10073, next=9643], Scope = 20

MiniCFG: UID 10214 CFBlock [
	t10081 = 2193
	t10079 = row_s10 * t10081 {canonical: (row_s10) * (2193)}
	t10093 = 3
	t10091 = col_s10 * t10093 {canonical: (col_s10) * (3)}
	t10078 = t10079 + t10091 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10110 = 0
	t10077 = t10078 + t10110 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	image_s0[t10077] = h_s12 {canonical: h_s12}
	t10127 = 2193
	t10125 = row_s10 * t10127 {canonical: (row_s10) * (2193)}
	t10139 = 3
	t10137 = col_s10 * t10139 {canonical: (col_s10) * (3)}
	t10124 = t10125 + t10137 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10156 = 1
	t10123 = t10124 + t10156 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	image_s0[t10123] = s_s12 {canonical: s_s12}
	t10173 = 2193
	t10171 = row_s10 * t10173 {canonical: (row_s10) * (2193)}
	t10185 = 3
	t10183 = col_s10 * t10185 {canonical: (col_s10) * (3)}
	t10170 = t10171 + t10183 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10202 = 2
	t10169 = t10170 + t10202 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	image_s0[t10169] = v_s12 {canonical: v_s12}
next=10215
], Scope = 12
UID 10215 CFEndOfMiniCFG [next=9643]

UID 9643 CFBlock [ miniCFG=10214, next=9585], Scope = 12

MiniCFG: UID 10223 CFBlock [
	t10219 = 1
	col_s10 += t10219
next=10224
], Scope = 11
UID 10224 CFEndOfMiniCFG [next=9585]

UID 9585 CFBlock [ miniCFG=10223, next=9644], Scope = 11

MiniCFG: UID 10279 CFBlock [
	t10232 = 2193
	t10230 = row_s10 * t10232 {canonical: (row_s10) * (2193)}
	t10244 = 3
	t10242 = col_s10 * t10244 {canonical: (col_s10) * (3)}
	t10229 = t10230 + t10242 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10261 = 0
	t10228 = t10229 + t10261 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	t10227 = image_s0[t10228]
	t10225 = max_s12 == t10227 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
next=10281
], Scope = 21
UID 10281 CFEndOfMiniCFG [next=9639]

UID 9639 CFConditional [ miniCFG=10279, boolTemp=t10225, ifTrue=9638, ifFalse=9637], Scope = 21

MiniCFG: UID 10381 CFBlock [
	t10288 = 2193
	t10286 = row_s10 * t10288 {canonical: (row_s10) * (2193)}
	t10300 = 3
	t10298 = col_s10 * t10300 {canonical: (col_s10) * (3)}
	t10285 = t10286 + t10298 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10317 = 1
	t10284 = t10285 + t10317 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	t10283 = image_s0[t10284]
	t10334 = 2193
	t10332 = row_s10 * t10334 {canonical: (row_s10) * (2193)}
	t10346 = 3
	t10344 = col_s10 * t10346 {canonical: (col_s10) * (3)}
	t10331 = t10332 + t10344 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10363 = 2
	t10330 = t10331 + t10363 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	t10329 = image_s0[t10330]
	t10282 = t10283 >= t10329 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) >= (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
next=10383
], Scope = 21
UID 10383 CFEndOfMiniCFG [next=9638]

UID 9638 CFConditional [ miniCFG=10381, boolTemp=t10282, ifTrue=9624, ifFalse=9637], Scope = 21

MiniCFG: UID 10505 CFBlock [
	t10388 = 60
	t10397 = 2193
	t10395 = row_s10 * t10397 {canonical: (row_s10) * (2193)}
	t10409 = 3
	t10407 = col_s10 * t10409 {canonical: (col_s10) * (3)}
	t10394 = t10395 + t10407 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10426 = 1
	t10393 = t10394 + t10426 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	t10392 = image_s0[t10393]
	t10443 = 2193
	t10441 = row_s10 * t10443 {canonical: (row_s10) * (2193)}
	t10455 = 3
	t10453 = col_s10 * t10455 {canonical: (col_s10) * (3)}
	t10440 = t10441 + t10453 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10472 = 2
	t10439 = t10440 + t10472 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	t10438 = image_s0[t10439]
	t10391 = t10392 - t10438 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	t10387 = t10388 * t10391 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))}
	h_s12 = t10387 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))) / (delta_s12)}
next=10506
], Scope = 22
UID 10506 CFEndOfMiniCFG [next=9624]

UID 9624 CFBlock [ miniCFG=10505, next=9643], Scope = 22

MiniCFG: UID 10561 CFBlock [
	t10514 = 2193
	t10512 = row_s10 * t10514 {canonical: (row_s10) * (2193)}
	t10526 = 3
	t10524 = col_s10 * t10526 {canonical: (col_s10) * (3)}
	t10511 = t10512 + t10524 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10543 = 0
	t10510 = t10511 + t10543 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	t10509 = image_s0[t10510]
	t10507 = max_s12 == t10509 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
next=10563
], Scope = 23
UID 10563 CFEndOfMiniCFG [next=9637]

UID 9637 CFConditional [ miniCFG=10561, boolTemp=t10507, ifTrue=9636, ifFalse=9635], Scope = 23

MiniCFG: UID 10663 CFBlock [
	t10570 = 2193
	t10568 = row_s10 * t10570 {canonical: (row_s10) * (2193)}
	t10582 = 3
	t10580 = col_s10 * t10582 {canonical: (col_s10) * (3)}
	t10567 = t10568 + t10580 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10599 = 1
	t10566 = t10567 + t10599 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	t10565 = image_s0[t10566]
	t10616 = 2193
	t10614 = row_s10 * t10616 {canonical: (row_s10) * (2193)}
	t10628 = 3
	t10626 = col_s10 * t10628 {canonical: (col_s10) * (3)}
	t10613 = t10614 + t10626 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10645 = 2
	t10612 = t10613 + t10645 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	t10611 = image_s0[t10612]
	t10564 = t10565 < t10611 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) < (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
next=10665
], Scope = 23
UID 10665 CFEndOfMiniCFG [next=9636]

UID 9636 CFConditional [ miniCFG=10663, boolTemp=t10564, ifTrue=9628, ifFalse=9635], Scope = 23

MiniCFG: UID 10798 CFBlock [
	t10669 = 360
	t10674 = 60
	t10683 = 2193
	t10681 = row_s10 * t10683 {canonical: (row_s10) * (2193)}
	t10695 = 3
	t10693 = col_s10 * t10695 {canonical: (col_s10) * (3)}
	t10680 = t10681 + t10693 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10712 = 1
	t10679 = t10680 + t10712 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	t10678 = image_s0[t10679]
	t10729 = 2193
	t10727 = row_s10 * t10729 {canonical: (row_s10) * (2193)}
	t10741 = 3
	t10739 = col_s10 * t10741 {canonical: (col_s10) * (3)}
	t10726 = t10727 + t10739 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10758 = 2
	t10725 = t10726 + t10758 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	t10724 = image_s0[t10725]
	t10677 = t10678 - t10724 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
	t10673 = t10674 * t10677 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))}
	t10672 = t10673 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))) / (delta_s12)}
	h_s12 = t10669 + t10672 {canonical: (360) + (((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]))) / (delta_s12))}
next=10799
], Scope = 24
UID 10799 CFEndOfMiniCFG [next=9628]

UID 9628 CFBlock [ miniCFG=10798, next=9643], Scope = 24

MiniCFG: UID 10854 CFBlock [
	t10807 = 2193
	t10805 = row_s10 * t10807 {canonical: (row_s10) * (2193)}
	t10819 = 3
	t10817 = col_s10 * t10819 {canonical: (col_s10) * (3)}
	t10804 = t10805 + t10817 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10836 = 1
	t10803 = t10804 + t10836 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	t10802 = image_s0[t10803]
	t10800 = max_s12 == t10802 {canonical: (max_s12) == (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
next=10856
], Scope = 25
UID 10856 CFEndOfMiniCFG [next=9635]

UID 9635 CFConditional [ miniCFG=10854, boolTemp=t10800, ifTrue=9632, ifFalse=9634], Scope = 25

MiniCFG: UID 10989 CFBlock [
	t10860 = 120
	t10865 = 60
	t10874 = 2193
	t10872 = row_s10 * t10874 {canonical: (row_s10) * (2193)}
	t10886 = 3
	t10884 = col_s10 * t10886 {canonical: (col_s10) * (3)}
	t10871 = t10872 + t10884 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10903 = 2
	t10870 = t10871 + t10903 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	t10869 = image_s0[t10870]
	t10920 = 2193
	t10918 = row_s10 * t10920 {canonical: (row_s10) * (2193)}
	t10932 = 3
	t10930 = col_s10 * t10932 {canonical: (col_s10) * (3)}
	t10917 = t10918 + t10930 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t10949 = 0
	t10916 = t10917 + t10949 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	t10915 = image_s0[t10916]
	t10868 = t10869 - t10915 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)])}
	t10864 = t10865 * t10868 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]))}
	t10863 = t10864 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]))) / (delta_s12)}
	h_s12 = t10860 + t10863 {canonical: (120) + (((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]))) / (delta_s12))}
next=10990
], Scope = 26
UID 10990 CFEndOfMiniCFG [next=9632]

UID 9632 CFBlock [ miniCFG=10989, next=9643], Scope = 26

MiniCFG: UID 11123 CFBlock [
	t10994 = 240
	t10999 = 60
	t11008 = 2193
	t11006 = row_s10 * t11008 {canonical: (row_s10) * (2193)}
	t11020 = 3
	t11018 = col_s10 * t11020 {canonical: (col_s10) * (3)}
	t11005 = t11006 + t11018 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t11037 = 0
	t11004 = t11005 + t11037 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	t11003 = image_s0[t11004]
	t11054 = 2193
	t11052 = row_s10 * t11054 {canonical: (row_s10) * (2193)}
	t11066 = 3
	t11064 = col_s10 * t11066 {canonical: (col_s10) * (3)}
	t11051 = t11052 + t11064 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t11083 = 1
	t11050 = t11051 + t11083 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	t11049 = image_s0[t11050]
	t11002 = t11003 - t11049 {canonical: (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)])}
	t10998 = t10999 * t11002 {canonical: (60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]))}
	t10997 = t10998 / delta_s12 {canonical: ((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]))) / (delta_s12)}
	h_s12 = t10994 + t10997 {canonical: (240) + (((60) * ((image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (0)]) - (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (1)]))) / (delta_s12))}
next=11124
], Scope = 27
UID 11124 CFEndOfMiniCFG [next=9634]

UID 9634 CFBlock [ miniCFG=11123, next=9643], Scope = 27

MiniCFG: UID 11147 CFBlock [
	t11129 = 1024
	t11128 = t11129 * delta_s12 {canonical: (1024) * (delta_s12)}
	s_s12 = t11128 / max_s12 {canonical: ((1024) * (delta_s12)) / (max_s12)}
next=11148
], Scope = 19
UID 11148 CFEndOfMiniCFG [next=9616]

UID 9616 CFBlock [ miniCFG=11147, next=9640], Scope = 19

MiniCFG: UID 11203 CFBlock [
	t11156 = 2193
	t11154 = row_s10 * t11156 {canonical: (row_s10) * (2193)}
	t11168 = 3
	t11166 = col_s10 * t11168 {canonical: (col_s10) * (3)}
	t11153 = t11154 + t11166 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t11185 = 2
	t11152 = t11153 + t11185 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	t11151 = image_s0[t11152]
	t11149 = min_s12 > t11151 {canonical: (min_s12) > (image_s0[(((row_s10) * (2193)) + ((col_s10) * (3))) + (2)])}
next=11205
], Scope = 16
UID 11205 CFEndOfMiniCFG [next=9608]

UID 9608 CFConditional [ miniCFG=11203, boolTemp=t11149, ifTrue=9607, ifFalse=9611], Scope = 16

MiniCFG: UID 11253 CFBlock [
	t11213 = 2193
	t11211 = row_s10 * t11213 {canonical: (row_s10) * (2193)}
	t11225 = 3
	t11223 = col_s10 * t11225 {canonical: (col_s10) * (3)}
	t11210 = t11211 + t11223 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t11242 = 2
	t11209 = t11210 + t11242 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (2)}
	min_s12 = image_s0[t11209]
next=11254
], Scope = 17
UID 11254 CFEndOfMiniCFG [next=9607]

UID 9607 CFBlock [ miniCFG=11253, next=9611], Scope = 17

MiniCFG: UID 11348 CFBlock [
	t11262 = 2193
	t11260 = row_s10 * t11262 {canonical: (row_s10) * (2193)}
	t11274 = 3
	t11272 = col_s10 * t11274 {canonical: (col_s10) * (3)}
	t11259 = t11260 + t11272 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t11291 = 1
	t11258 = t11259 + t11291 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (1)}
	max_s12 = image_s0[t11258]
	t11308 = 2193
	t11306 = row_s10 * t11308 {canonical: (row_s10) * (2193)}
	t11320 = 3
	t11318 = col_s10 * t11320 {canonical: (col_s10) * (3)}
	t11305 = t11306 + t11318 {canonical: ((row_s10) * (2193)) + ((col_s10) * (3))}
	t11337 = 0
	t11304 = t11305 + t11337 {canonical: (((row_s10) * (2193)) + ((col_s10) * (3))) + (0)}
	min_s12 = image_s0[t11304]
next=11349
], Scope = 14
UID 11349 CFEndOfMiniCFG [next=9599]

UID 9599 CFBlock [ miniCFG=11348, next=9609], Scope = 14

MiniCFG: UID 11357 CFBlock [
	t11353 = 1
	row_s10 += t11353
next=11358
], Scope = 10
UID 11358 CFEndOfMiniCFG [next=9581]

UID 9581 CFBlock [ miniCFG=11357, next=9645], Scope = 10
UID 9646 CFReturn
----------
