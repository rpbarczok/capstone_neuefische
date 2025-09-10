import { createContext } from 'react'

const startTheme = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'

export const ThemeContext = createContext(startTheme)