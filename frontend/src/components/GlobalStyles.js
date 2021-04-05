import { createGlobalStyle } from 'styled-components/macro';

const GlobalStyle = createGlobalStyle`
 html, body, #root, ol, ul, li, p, h1, h2, h3, h4, h5 {
   margin: 0;
   padding: 0;
 }

 body {
  /* Improve font rendering reg. thickness*/
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; 
  font-smoothing: antialiased;

  /* Common */
  --letter-spacing: 0.02rem;

  /* Titles */
  --font-family-title: 'DM Serif Display', serif;
  --font-weight-title: 600;
  --font-size-title-xs: 0.8rem;
  --font-size-title-s: 1rem;
  --font-size-title-m: 1.5rem;
  --font-size-title-l: 2rem;
  --margin-title: 0 0 1rem;
  --margin-title-s: 0 0 0.4rem;

  /* Subtitles */
  --font-family-subtitle: 'Source Serif Pro', serif;
  --font-style-subtitle: italic;
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

  /* Colors */
  --color-light-silver: #c5c5c5;
  --color-medium-silver: #454545;
  --color-dark-silver: #2b2b2b;
  --color-rich-black: #121212;
  --color-dark-byzantium: #4c2a42;
  --color-light-green: #84e296;
  --color-paradise-pink: #ec4067;
  --color-sizzling-red: #ef5d60;
  --color-gold-crayola: #edc79b;

  /* Buttons */
  --font-family-button: 'DM Serif Display', serif;
  --font-size-button: 1rem;

  /* Inputs */
  --font-family-input: 'Source Serif Pro', serif;
  --font-size-input: .9rem;

  /* Brand Gradient */
  --gradient-primary: linear-gradient(90deg, rgba(237,199,155,1) 0%, rgba(236,64,103,1) 100%);
  --gradient-secondary: linear-gradient(90deg, rgba(237,199,155,1) 0%, rgba(132,226,150,1) 100%);

  /* Sizes, padding & margin */
  --container-max-width: 1000px;
  --container-padding: 20px;

  /* Transitions */
  --transition-color: color 0.3s;
  --transition-opacity: opacity 0.3s;
  --transition-transform: transform 0.5s ease-in-out;

  /* Hovers */
  --opacity-hover: 0.8;

  /* Disblaed */
  --opacity-disabled: 0.5;

  /* Border */
  --border-radius: 7px;

  /* Max Width */
  --max-width: 1000px;
  
  /* Defaults */
  --default-margin: 2rem;

  /* For All Settings */
  font-family: var(--font-family-content);
  font-size: .9rem;
  letter-spacing: var(--letter-spacing);
  color: var(--color-medium-silver);
 }

`;

export default GlobalStyle;
