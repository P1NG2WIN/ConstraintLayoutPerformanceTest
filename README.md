# ConstraintLayoutPerformanceTest

This one comes from the blogpost: https://medium.com/@krpiotrek/constraintlayout-performance-c1455c7984d7

Here are the results — an average for 1000 iterations on a Android 9.0 Samsung S8 Active device (time in ns, the lower the better)

```
Linear    :	inflate:	12800666603	measure:	1944024116	layout:	162693246	total:	14907383965
Relative  :	inflate:	11924617440	measure:	1827840427	layout:	134245358	total:	13886703225
Constraint:	inflate:	12684245264	measure:	2896157869	layout:	150701459	total:	15731104592
```
