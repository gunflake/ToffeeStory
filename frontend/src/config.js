export default {
  PostMethods: {
    LIKE: 0,
    BOOKMARK: 1,
    ALL: 2,
    MYMENU: 3,
    SEARCH: 4
  },

  ProfileMethods: {
    INSTAGRAM_URL: 'https://www.instagram.com/',
    TWITTER_URL: 'https://www.twitter.com/',
    CODE_MY_MENU: 3,
    CODE_LIKES: 0,
    CODE_BOOKMARKS: 1
  },

  AccSettingsMethods: {
    MAX_SIZE: 5 * 1024 * 1024,
    CONFIRM_MSG: 'Are you sure?',
    UNDONE_CONFIRM_MSG: 'Are you sure? This action cannot be undone.',
    FILE_OVERSIZE_MSG: 'Profile image must be less than 5MB. Try reducing the size of image.',
    IMG_UPLOAD_MSG: 'Profile image updated',
    FULL_NAME_MSG: 'Full Name is invalid (2 - 30 characters)',
    USER_NAME_MSG: 'User Name is invalid (2 - 30 characters)',
    REQUIRED_VAL_MSG: 'Full Name and User Name are required.',
    INPUT_CHK_MSG: 'Please check your input again.',
    NEW_PWD_MSG: 'New Password is invalid (8 - 20 characters, include alphabets, numbers and special characters)',
    CONFIRM_PWD_MSG: 'Password confirmation doesn\'t match New Password',
    CHANGE_PWD_MSG: 'Password confirmation doesn\'t match New Password',
    ENTER_ALL_INFO_MSG: 'Please enter all the information.',
    ENTER_PWD_MSG: 'Please enter password.',
    FULL_NAME_REGEX: /^[가-힣a-zA-Z\s]{2,30}$/,
    USER_NAME_REGEX: /^[가-힣a-zA-Z0-9_]{2,30}$/,
    PWD_REGEX: /(?=.*\d{1,50})(?=.*[~`!@#$%^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,20}$/,
    TYPE_ERROR: 'red',
    TYPE_OK: 'green'
  }
}
