import {useEffect, useState} from "react";
import { ThemeContext } from './contexts/ThemeContext.ts';
import {LoadingContext} from "./contexts/LoadingContext.ts";
import BasicStructure from "./BasicStructure.tsx";

function App() {

    const [theme, setTheme] = useState<'light' | 'dark'>(window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light')
    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        if (theme === 'light') {
            document.getElementsByTagName('html')[0].setAttribute('data-bs-theme', 'light')
        } else {
            document.getElementsByTagName('html')[0].setAttribute('data-bs-theme', 'dark')
        }
    }, [])

    return (
        <ThemeContext.Provider value={theme}>
            <LoadingContext.Provider value={{ isLoading: isLoading, setIsLoading: setIsLoading }}>
                <BasicStructure
                    setTheme={setTheme}
                    theme={theme}
                    isLoading={isLoading}/>
          </LoadingContext.Provider>
      </ThemeContext.Provider>
    )
}

export default App
