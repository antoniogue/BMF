

#ifndef PRECOMPILER_RULES
#define PRECOMPILER_RULES

//#include "BMF.h"

#if DEBUG != NO_DEBUG
#include "printf.h"
#endif

// MAX_REQUESTS should be defined in the buildingManagement.extra file or at compilation time
#ifndef MAX_REQUESTS
#define MAX_REQUESTS 10
#endif


// the following lines are to resize the MAX_REQUESTS number if it is too big or too small
#if MAX_REQUESTS >25
#undef MAX_REQUESTS
#define MAX_REQUESTS 25
#endif

#if MAX_REQUESTS <1
#undef MAX_REQUESTS
#define MAX_REQUESTS 1
#endif



#endif //PRECOMPILER_RULES