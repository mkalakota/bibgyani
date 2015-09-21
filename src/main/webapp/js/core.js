/**
 * Core
 */
nebib.core = {};

nebib.core.amounts = [ // question amounts
4000, // first question
8000, // second question
12000, // third question
16000, // fourth question
20000,// fifth question
26000,// sixth question
32000,// seventh question
38000,// eighth question
44000,// ninth question
50000,// tenth question
60000,// eleventh question
70000,// twelfth question
80000,// thirteenth question
90000,// fourteenth question
100000 // fifteenth question
];

nebib.core.option_status = {
	SELECTED : "SELECTED",
	NOT_SELECTED : "NOT_SELECTED",
	RIGHT : "RIGHT",
	RIGHT_HOST : "RIGHT_HOST",
	RIGHT_SELECTED : "RIGHT_SELECTED",
	RIGHT_NOT_SELECTED : "RIGHT_NOT_SELECTED",
	WRONG : "WRONG",
	WRONG_SELECTED : "WRONG_SELECTED",
	WRONG_NOT_SELECTED : "WRONG_NOT_SELECTED",
	NONE : "NONE"
};

nebib.core.commands = {
	NEW_GAME : "new_game",
	FINISH_GAME : "finish_game",

	NEXT_QUESTION : "next_question",

	START_TIMER : "start_timer",
	PAUSE_TIMER : "pause_timer",
	RESUME_TIMER : "resume_timer",

	SHOW_ANSWER : "show_answer",
	USE_LIFELINE : "use_lifeline",
	USE_LIFELINE_DUAL : "use_lifeline_dual",

	TOGGLE_BG : "toggle_background",
	FLIP_QUESTION : "flip_question",
};
