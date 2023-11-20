//package webapp.gooutmap;
//
//import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//public class Test {
//
//
//        /**
//         * index
//         */
//        @RequestMapping(path = "home", method = RequestMethod.GET)
//        public ModelAndView index(ModelAndView mav) {
//
//            // messageに値を設定
//            mav.addObject("message", "hello world");
//            mav.setViewName("index");
//            return mav;
//        }
//
//        /**
//         * 入力画面表示
//         */
//        @RequestMapping(path = "form", method = RequestMethod.GET)
//        public ModelAndView form(ModelAndView mav, ConfigurationPropertyName.Form form) {
//
//            // formのnameに初期値を設定
//            form.setName("hoge");
//            mav.addObject("form", form);
//            mav.setViewName("demoForm");
//            return mav;
//        }
//
//        /**
//         * 結果受け取り、validation
//         */
//        @RequestMapping(path = "form", method = RequestMethod.POST)
//        public ModelAndView formPost(ModelAndView mav, @Valid @ModelAttribute ConfigurationPropertyName.Form form,
//                                     BindingResult result) {
//
//            // validationのチェック
//            if (result.hasFieldErrors()) {
//                mav.addObject("errors", result.getFieldErrors());
//                mav.addObject("form", form);
//                mav.setViewName("demoForm");
//                return mav;
//            }
//
//            // formの値を保存
//            formService.saveData(form);
//
//            mav.setViewName("ok");
//            return mav;
//        }
//
//    }
//
//}
