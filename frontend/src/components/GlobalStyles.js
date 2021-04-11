import { createGlobalStyle } from 'styled-components/macro';

const GlobalStyle = createGlobalStyle`

 html, body {
   margin: 0;
   padding: 0;
 }

 body {

  /*
   * RESET MARGIN & PADDING #################
   */

  ol, ul, li, p, h1, h2, h3, h4, h5 {
    margin: 0;
    padding: 0;
  }

  /*
   * TYPOGRAPHY #############################
   */

  /* Improve font rendering reg. thickness*/
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; 
  font-smoothing: antialiased;

  /* Common */
  --letter-spacing: 0.02rem;

  /* Titles */
  --font-family-title: 'DM Serif Display', serif;
  --font-weight-title: 600;
  --font-weight-title-slim: 400;
  --font-size-title-xs: 0.8rem;
  --font-size-title-s: 1rem;
  --font-size-title-s2: 1.3rem;
  --font-size-title-m: 1.5rem;
  --font-size-title-l: 2rem;
  --font-size-title-xl: 3rem;
  --margin-title: 0 0 1rem;
  --margin-title-s: 0 0 0.4rem;

  /* Subtitles */
  --font-family-subtitle: 'Source Serif Pro', serif;
  --font-style-subtitle: italic;
  --font-weight-title-slim: 400;
  --font-weight-title-extraslim: 300;
  --font-size-subtitle-m: .8rem;
  --font-size-subtitle-l: 1rem;
  --margin-subtitle: 0 0 1.5rem;

  /* Navigation */
  --font-family-navigation: 'DM Serif Display', serif;
  --font-size-navigation: 1.1rem;
  
  /* Regular size text */
  --font-family-content: 'Source Serif Pro', serif;
  --font-size-content: .9rem;

  /* Medium size text */
  --font-size-medium-content: .8rem;

  /* Buttons */
  --font-family-button: 'DM Serif Display', serif;
  --font-size-button: .9rem;

  /* Inputs */
  --font-family-input: 'Source Serif Pro', serif;
  --font-size-input: .9rem;

  /* Line Heights */
  --line-height: 1.5rem;

  /*
   * COLOR SCHEME ###########################
   */

  --color-lightest-silver: #efefef;
  --color-lighter-silver: #c5c5c5;
  --color-light-silver: #505050;
  --color-medium-silver: #454545;
  --color-dark-silver: #2b2b2b;
  --color-rich-black: #121212;
  --color-dark-byzantium: #4c2a42;
  --color-light-green: #84e296;
  --color-paradise-pink: #ec4067;
  --color-sizzling-red: #ef5d60;
  --color-gold-crayola: #edc79b;

  --gradient-primary: linear-gradient(90deg, rgba(237,199,155,1) 0%, rgba(236,64,103,1) 100%);
  --gradient-secondary: linear-gradient(90deg, rgba(237,199,155,1) 0%, rgba(132,226,150,1) 100%);

  /*
   * TRANSITION / ANIMATION / EFFECT #########
   */

  --transition-color: color 0.3s ease-in-out;
  --transition-opacity: opacity 0.3s ease-in-out;
  --transition-transform: transform 0.5s ease-in-out;

  --opacity-hover: 0.8;
  --opacity-disabled: 0.5;

  /*
   * DEFAULT ELEMENT STYLES ##################
   */

  --box-shadow: 0 5px 10px 0 rgb(0 0 0 / 8%);

  --border-radius: 7px;

  /*
   * SIZE, PADDING & MARGIN ##################
   */

  /* Padding  */
  --default-padding-s: 1.25rem;
  --default-padding-s2: calc(var(--default-padding-s) * 1.5);
  --default-padding-m: calc(var(--default-padding-s) * 2);
  --default-padding-m2: calc(var(--default-padding-s) * 2.5);
  --default-padding-l: calc(var(--default-padding-s) * 3);
  --default-padding-l2: calc(var(--default-padding-s) * 3.5);
  --default-padding-xl: calc(var(--default-padding-s) * 5);

  --default-margin: 2rem;

  --max-width-s: 425px;
  --max-width-m: 768px;
  --max-width-l: 1000px;
  --max-width-xl: 1360px;
  
  /*
   * GLOBAL STYLES ###########################
   */

  font-family: var(--font-family-content);
  font-size: .9rem;
  letter-spacing: var(--letter-spacing);
  color: var(--color-medium-silver);
  }
`;

export default GlobalStyle;
