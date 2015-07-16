/**/

#include "PrecompilerRules.c"
#include "BMF.h"


configuration RequestsManagerC{
	provides interface RequestsManager;
}
implementation {
	components RequestsManagerP as RM; 
	components MainC;
	
	RequestsManager = RM;
	RM.Boot -> MainC.Boot;

#define NEW_TIMER(id) \
  components new TimerMilliC() as Timer##id; \
  RM.Timers[id] -> Timer##id; \
  components new TimerMilliC() as LifetimeTimer##id; \
  RM.LifetimeTimers[id] -> LifetimeTimer##id;

#if MAX_REQUESTS >=1
	NEW_TIMER(0);
#endif
	
#if MAX_REQUESTS >=2
	NEW_TIMER(1);
#endif

#if MAX_REQUESTS >=3
	NEW_TIMER(2);
#endif

#if MAX_REQUESTS >=4
	NEW_TIMER(3);
#endif
	
#if MAX_REQUESTS >=5
	NEW_TIMER(4);
#endif
#if MAX_REQUESTS >=6
	NEW_TIMER(5);
#endif

#if MAX_REQUESTS >=7
	NEW_TIMER(6);
#endif

#if MAX_REQUESTS >=8
	NEW_TIMER(7);
#endif

#if MAX_REQUESTS >=9
	NEW_TIMER(8);
#endif

#if MAX_REQUESTS >=10
	NEW_TIMER(9);
#endif

#if MAX_REQUESTS >=11
	NEW_TIMER(10);
#endif

#if MAX_REQUESTS >=12
	NEW_TIMER(11);
#endif

#if MAX_REQUESTS >=13
	NEW_TIMER(12);
#endif

#if MAX_REQUESTS >=14
	NEW_TIMER(13);
#endif

#if MAX_REQUESTS >=15
	NEW_TIMER(14);
#endif

#if MAX_REQUESTS >=16
	NEW_TIMER(15);
#endif

#if MAX_REQUESTS >=17
	NEW_TIMER(16);
#endif

#if MAX_REQUESTS >=18
	NEW_TIMER(17);
#endif

#if MAX_REQUESTS >=19
	NEW_TIMER(18);
#endif

#if MAX_REQUESTS >=20
	NEW_TIMER(19);
#endif

#if MAX_REQUESTS >=21
	NEW_TIMER(20);
#endif

#if MAX_REQUESTS >=22
	NEW_TIMER(21);
#endif

#if MAX_REQUESTS >=23
	NEW_TIMER(22);
#endif

#if MAX_REQUESTS >=24
	NEW_TIMER(23);
#endif

#if MAX_REQUESTS >=25
	NEW_TIMER(24);
#endif

}



